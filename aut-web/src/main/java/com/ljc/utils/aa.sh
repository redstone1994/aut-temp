#!/bin/bash

set -e

red='\033[0;31m'
green='\033[0;32m'
yellow='\033[0;33m'
plain='\033[0m'

#[[ $EUID -ne 0 ]] && echo -e "[${red}Error${plain}] This script must be run as root!" && exit 1

version=(k8s-mater k8s-salve docker)


disable_selinux(){
    if [ -s /etc/selinux/config ] && grep 'SELINUX=enforcing' /etc/selinux/config; then
        sed -i 's/SELINUX=enforcing/SELINUX=disabled/g' /etc/selinux/config
        setenforce 0
    fi
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
conf_hosts(){
	echo "192.168.14.180 k8smaster" >> /etc/hosts
	echo "192.168.14.181 k8snode1" >> /etc/hosts
	echo "192.168.14.182 k8snode2" >> /etc/hosts
	cat /etc/hosts
}

create_k8sconf(){
	echo -e "[${green}INFO:${plain}] 开始创建k8s配置文件==========="
	#net.ipv4.ip_forward = 1
	cat > /etc/sysctl.d/k8s.conf <<-EOF
	net.bridge.bridge-nf-call-ip6tables = 1
	net.bridge.bridge-nf-call-iptables = 1
	net.ipv4.ip_forward = 1
	EOF

	modprobe br_netfilter
	sysctl -p /etc/sysctl.d/k8s.conf
	echo -e "[${green}INFO:${plain}] 创建k8s配置文件结束==========="

}

install_docker(){
	echo -e "[${green}INFO:${plain}] 开始安装docker==========="
	yum install -y yum-utils device-mapper-persistent-data lvm2

	yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

	yum makecache fast
	yum install -y --setopt=obsoletes=0 \
  		docker-ce-17.09.1.ce-1.el7.centos
	curl -sSL https://get.daocloud.io/daotools/set_mirror.sh | sh -s http://f1361db2.m.daocloud.io
	systemctl start docker
	systemctl enable docker

	iptables -P FORWARD ACCEPT
	echo -e "[${green}INFO:${plain}] 安装docker完成==========="

}




install
