# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "Unknown"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-joshheyse.git;protocol=ssh;branch=master \
           file://0001-update-makefile.patch \
           file://ldd-misc-modules \
           file://misc-modules_load \
           file://misc-modules_unload \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "f83ca2eef39ee8fabc8dac217543abf33188817f"

S = "${WORKDIR}/git"

inherit module
inherit update-rc.d

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "ldd-misc-modules"

FILES:${PN} += "${bindir}/misc-modules_load"
FILES:${PN} += "${bindir}/misc-modules_unload"
FILES:${PN} += "${sysconfdir}/init.d/ldd-misc-modules"

do_install:append () {
  install -d ${D}${bindir}
  install -m 0755 ${WORKDIR}/misc-modules_load ${D}${bindir}/
  install -m 0755 ${WORKDIR}/misc-modules_unload ${D}${bindir}/

  install -d ${D}${sysconfdir}/init.d
  install -m 0744 ${WORKDIR}/ldd-misc-modules ${D}${sysconfdir}/init.d/
}
