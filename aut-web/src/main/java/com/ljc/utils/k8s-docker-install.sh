#!/bin/bash

set -e

red='\033[0;31m'
green='\033[0;32m'
yellow='\033[0;33m'
plain='\033[0m'

[[ $EUID -ne 0 ]] && echo -e "[${red}Error${plain}] This script must be run as root!" && exit 1

version=(k8s-mater k8s-salve docker)

conf_hosts(){
	echo "192.168.1.180 k8s-master" >> /etc/hosts
	echo "192.168.1.181 k8s-node1" >> /etc/hosts
	echo "192.168.1.182 k8s-node2" >> /etc/hosts
	echo "192.168.1.183 k8s-node3" >> /etc/hosts
	echo "192.168.1.190 harbor" >> /etc/hosts
	cat /etc/hosts
}

disable_selinux(){
    if [ -s /etc/selinux/config ] && grep 'SELINUX=enforcing' /etc/selinux/config; then
        sed -i 's/SELINUX=enforcing/SELINUX=disabled/g' /etc/selinux/config
        setenforce 0
    fi
}

yum_ini(){
    if [ -s /etc/yum.repos.d/CentOS-Base.repo ] ; then
	mv /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.bak
	curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
	fi
	yum clean all
	yum makecache
	yum -y install wget
}

disable_swap(){
#关闭swap
swapoff -a
yes | cp /etc/fstab /etc/fstab_bak
cat /etc/fstab_bak |grep -v swap > /etc/fstab

}

disable_firewall(){
	echo -e "[${green}INFO:${plain}] Start disable firewall..."
		systemctl stop firewalld
		systemctl disable firewalld
	echo -e "[${green}INFO:${plain}] Turn off firewall complete..."
}
date_config(){
#时间同步
yum install -y ntpdate
ntpdate -u ntp.api.bz
}

install_select(){
echo "Please select the installation option"
while true
do
for ((i=1;i<=${#version[@]};i++ )); do
        hint="${version[$i-1]}"
        echo -e "${green}${i}${plain}) ${hint}"
done

read  -p "please enter a number:" option
    case "${option}" in
        1|2|3)
        echo
        echo -e "${green}[INFO]${plain} You cloose ${version[${option}-1]}"
        echo
        break
        ;;
        *)
        echo -e "${green}[INFO]${plain} Please choose [1-3]"
        ;;
    esac
done
}

is_64bit(){
    if [ `getconf WORD_BIT` = '32' ] && [ `getconf LONG_BIT` = '64' ] ; then
        return 0
    else
        return 1
    fi
}

check_sys() {
    local checkType=$1
    local value=$2

    local release=''
    local systemPackage=''

    if [ -f /etc/redhat-release ]; then
        release="centos"
        systemPackage="yum"
    elif cat /etc/issue | grep -Eqi "debian"; then
        release="debian"
        systemPackage="apt"
    elif cat /etc/issue | grep -Eqi "ubuntu"; then
        release="ubuntu"
        systemPackage="apt"
    elif cat /etc/issue | grep -Eqi "centos|red hat|redhat"; then
        release="centos"
        systemPackage="yum"
    elif cat /proc/version | grep -Eqi "debian"; then
        release="debian"
        systemPackage="apt"
    elif cat /proc/version | grep -Eqi "ubuntu"; then
        release="ubuntu"
        systemPackage="apt"
    elif cat /proc/version | grep -Eqi "centos|red hat|redhat"; then
        release="centos"
        systemPackage="yum"
    fi

    if [ ${checkType} == "sysRelease" ]; then
        if [ "$value" == "$release" ]; then
            return 0
        else
            return 1
        fi
    elif [ ${checkType} == "packageManager" ]; then
        if [ "$value" == "$systemPackage" ]; then
            return 0
        else
            return 1
        fi
    fi
}

getversion() {
    if [[ -s /etc/redhat-release ]]; then
        grep -oE  "[0-9.]+" /etc/redhat-release
    else
        grep -oE  "[0-9.]+" /etc/issue
    fi
}

centosversion() {
    if check_sys sysRelease centos; then
        local code=$1
        local version="$(getversion)"
        local main_ver=${version%%.*}
        if [ "$main_ver" == "$code" ]; then
            return 0
        else
            return 1
        fi
    else
        return 1
    fi
}


create_k8sconf(){
	echo -e "[${green}INFO:${plain}] Start creating the k8s configuration file..."

	cat > /etc/sysctl.d/k8s.conf <<-EOF
	net.bridge.bridge-nf-call-ip6tables = 1
	net.bridge.bridge-nf-call-iptables = 1
	net.ipv4.ip_forward = 1
	vm.swappiness=0
	EOF

	modprobe br_netfilter
	sysctl -p /etc/sysctl.d/k8s.conf
	echo -e "[${green}INFO:${plain}] Create k8s configuration file to complete..."

}



install_docker(){
	echo -e "[${green}INFO:${plain}] Start installing docker..."
	yum install -y yum-utils device-mapper-persistent-data lvm2
	yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
	yum makecache fast

	yum install -y --setopt=obsoletes=0 \
  		docker-ce-18.06.1.ce-3.el7

	#curl -sSL https://get.daocloud.io/daotools/set_mirror.sh | sh -s http://f1361db2.m.daocloud.io
	mkdir -p /etc/docker
	cat > /etc/docker/daemon.json <<-EOF
	{
	  "registry-mirrors": ["https://jb7lu0cu.mirror.aliyuncs.com"],
	  "exec-opts": ["native.cgroupdriver=systemd"],
	  "log-driver": "json-file",
	  "log-opts": {
	    "max-size": "50m"
	  },
	  "storage-driver": "overlay2",
	  "storage-opts": [
        "overlay2.override_kernel_check=true"
      ]

	}
	EOF
	systemctl start docker
	systemctl enable docker
    docker version
	echo -e "[${green}INFO:${plain}] Install docker to complete..."

}

k8s_master_ini(){
	echo -e "[${green}INFO:${plain}] Start installing k8s-master dependency docker image..."

	local images=(kube-proxy:v1.14.1
		kube-scheduler:v1.14.1
		kube-controller-manager:v1.14.1
		kube-apiserver:v1.14.1
		etcd:3.3.10
		pause:3.1
		coredns:1.3.1)

	for image in ${images[@]} ; do
	  docker pull ljc359120730/$image
	  docker tag ljc359120730/$image k8s.gcr.io/$image
	  docker rmi ljc359120730/$image
	done

	echo -e "[${green}INFO:${plain}] Installation of k8s-master depends on docker image..."
}

k8s_salve_ini(){
    echo -e "[${green}INFO:${plain}] Start installing k8s-slave dependency docker image..."

	local images=(kube-proxy:v1.14.1
			pause:3.1
			coredns:1.3.1)

	for image in ${images[@]} ; do
	  docker pull ljc359120730/$image
	  docker tag ljc359120730/$image k8s.gcr.io/$image
	  docker rmi ljc359120730/$image
	done

	echo -e "[${green}INFO:${plain}] Installation of k8s-slave depends on docker image..."
}

install_kubernetes(){
	echo -e "[${green}INFO:${plain}] Start installing k8s..."
	cat > /etc/yum.repos.d/kubernetes.repo <<-EOF
	[kubernetes]
	name=Kubernetes
	baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64/
	enabled=1
	gpgcheck=0
	repo_gpgcheck=0
	gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg
		   https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
	EOF

	yum makecache fast
	yum install -y kubelet-1.14.1 kubeadm-1.14.1 kubectl-1.14.1 \
	    --disableexcludes=kubernetes
	systemctl enable kubelet && systemctl start kubelet

	echo -e "[${green}INFO:${plain}] Installation k8s completed..."

}

init_kubernetes(){
	echo -e "[${green}INFO:${plain}] start initializing kubernetes..."

	#echo  "KUBELET_EXTRA_ARGS=--fail-swap-on=false" > /etc/sysconfig/kubelet

	kubeadm init \
       --kubernetes-version=v1.14.1 \
       --pod-network-cidr=10.244.0.0/16 \
       --ignore-preflight-errors=all
       #--apiserver-advertise-address=$ipaddr \
      export KUBECONFIG=/etc/kubernetes/admin.conf
      source ~/.bash_profile

      mkdir -p $HOME/.kube
      cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
      chown $(id -u):$(id -g) $HOME/.kube/config
      kubectl version
      echo -e "[${green}INFO:${plain}] Initialization k8s complete..."
}

main(){
if is_64bit&&getversion 7; then
    install_select
    echo "${option}"
    if [ "${option}" == "1" ]; then
        yum_ini
        conf_hosts
        disable_swap
        disable_firewall
        date_config
        create_k8sconf
        install_docker
        disable_selinux
        k8s_master_ini
        install_kubernetes
        init_kubernetes
    elif [ "${option}" == "2" ]; then
        yum_ini
        conf_hosts
        disable_swap
        disable_firewall
        date_config
        create_k8sconf
        install_docker
        disable_selinux
        k8s_salve_ini
        install_kubernetes
    elif [ "${option}" == "3" ]; then
        yum_ini
        disable_swap
        disable_firewall
        date_config
        install_docker
        disable_selinux
    else
        echo echo -e "[${red}Error${plain}] Your system does not support this script !!!"
    fi

fi

}

main
