#!ipxe
set local_address http://172.31.2.2
set script_repo http://172.31.2.2/ipxe-scripts
#console --picture ${local_address}/images/Viking-logo.png

menu Network Boot Menu
item exit Reboot
item clone  Boot Clonezilla
item dban DBAN
item alpine Alpine Linux base OS
item windows  Windows Installers
item install  Linux Installers
item live   Linux Live Images
item network  Networking Images
choose target && goto ${target}

:windows
boot ${script_repo}/windows.ipxe

:install
boot ${script_repo}/linux-install.ipxe

:live
boot ${script_repo}/live.ipxe

:network
boot ${script_repo}/network.ipxe

:exit
reboot

:clone
kernel ${local_address}/clonezilla/live/vmlinuz initrd=initrd.img boot=live username=user union=overlay config components quiet noswap edd=on nomodeset nodmraid locals= keyboard-layout= ocs_live_run="ocs-live-general" ocs_live_extra_param="" ocs_live_batch=no net.ifnames=0 noprompt fetch=${local_address}/clonezilla/live/filesystem.squashfs
initrd ${local_address}/clonezilla/live/initrd.img
boot

:alpine
kernel ${local_address}/alpine/vmlinuz-lts console=tty0 modules=loop,squashfs quiet nomodeset alpine_repo=https://dl-cdn.alpinelinux.org/alpine/v3.17/main modloop=${local_address}/alpine/modloop-lts
initrd ${local_address}/alpine/initramfs-lts
boot

:dban
sanboot ${local_address}/dban-2.3.0_i586.iso