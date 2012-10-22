# ApkAssert

ApkAssert is released under the [Apache License Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)

- [Cert.java](https://github.com/litl/ApkAssert/blob/master/apkassert/src/main/java/apkassert/Cert.java)
  - Ensures all files are signed
  - Ensures cert name does not contain default or debug

- [InstallLocation.java](https://github.com/litl/ApkAssert/blob/master/apkassert/src/main/java/apkassert/InstallLocation.java)
  - Parses AndroidManifest.xml binary and returns install location

[Sample.java](https://github.com/litl/ApkAssert/blob/master/apkassert/src/main/java/apkassert/example/Sample.java) demonstrates checking APK install location, size, and certificate.

#### 3rd party code

- [axml](http://code.google.com/p/axml/)