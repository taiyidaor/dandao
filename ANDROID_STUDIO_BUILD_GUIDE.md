# Android Studio Otter 构建APK详细指南

本指南将详细介绍如何使用Android Studio Otter 3 Feature Drop (2025.2.3)来构建您的分段计时器应用APK。

## 前提条件

1. 已安装Android Studio Otter 3 Feature Drop (2025.2.3)
2. 已安装Java Development Kit (JDK) 17或更高版本
3. 已配置Android SDK（Android Studio通常会自动配置）

## 步骤1：准备项目文件

首先，确保您的项目文件结构正确。我们需要将现有的Java文件和资源文件组织成Android Studio可以识别的结构。

### 创建标准Android项目结构

1. 打开终端或命令提示符
2. 导航到项目目录：
   ```bash
   cd /home/user/vibecoding/workspace/android_timer_app_native
   ```
3. 创建标准Android项目结构：
   ```bash
   mkdir -p TimerApp/app/src/main/java/com/example/timerapp
   mkdir -p TimerApp/app/src/main/res/layout
   mkdir -p TimerApp/app/src/main/res/values
   mkdir -p TimerApp/app/src/main/res/drawable
   mkdir -p TimerApp/app/src/main/res/menu
   ```

### 复制源文件到正确位置

1. 复制Java源文件：
   ```bash
   cp MainActivity.java TimerApp/app/src/main/java/com/example/timerapp/
   cp TimerDetailActivity.java TimerApp/app/src/main/java/com/example/timerapp/
   cp AddTimerDialog.java TimerApp/app/src/main/java/com/example/timerapp/
   cp TimerService.java TimerApp/app/src/main/java/com/example/timerapp/
   cp NotificationUtil.java TimerApp/app/src/main/java/com/example/timerapp/
   cp TimerStorage.java TimerApp/app/src/main/java/com/example/timerapp/
   cp TimerPack.java TimerApp/app/src/main/java/com/example/timerapp/
   cp SubTimer.java TimerApp/app/src/main/java/com/example/timerapp/
   cp TimerPackAdapter.java TimerApp/app/src/main/java/com/example/timerapp/
   cp SubTimerAdapter.java TimerApp/app/src/main/java/com/example/timerapp/
   ```

2. 复制AndroidManifest.xml：
   ```bash
   mkdir -p TimerApp/app/src/main/
   cp AndroidManifest.xml TimerApp/app/src/main/
   ```

3. 复制资源文件：
   ```bash
   cp -r res/* TimerApp/app/src/main/res/
   ```

## 步骤2：创建Android Studio项目配置文件

### 创建build.gradle文件

1. 创建项目级build.gradle：
   ```bash
   cat > TimerApp/build.gradle << 'EOF'
   // Top-level build file where you can add configuration options common to all sub-projects/modules.
   plugins {
       id 'com.android.application' version '8.5.0' apply false
   }
   EOF
   ```

2. 创建应用级build.gradle：
   ```bash
   cat > TimerApp/app/build.gradle << 'EOF'
   plugins {
       id 'com.android.application'
   }

   android {
       namespace 'com.example.timerapp'
       compileSdk 35

       defaultConfig {
           applicationId "com.example.timerapp"
           minSdk 21
           targetSdk 35
           versionCode 1
           versionName "1.0"

           testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
       }

       buildTypes {
           release {
               minifyEnabled false
               proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
           }
       }
       compileOptions {
           sourceCompatibility JavaVersion.VERSION_17
           targetCompatibility JavaVersion.VERSION_17
       }
       buildFeatures {
           viewBinding true
       }
   }

   dependencies {
       implementation 'androidx.appcompat:appcompat:1.7.0'
       implementation 'com.google.android.material:material:1.12.0'
       implementation 'androidx.constraintlayout:constraintlayout:2.2.0'
       implementation 'androidx.recyclerview:recyclerview:1.4.0'
       implementation 'androidx.cardview:cardview:1.0.0'
       testImplementation 'junit:junit:4.13.2'
       androidTestImplementation 'androidx.test.ext:junit:1.2.1'
       androidTestImplementation 'androidx.test.espresso:espresso-core:3.6.1'
   }
   EOF
   ```

### 创建settings.gradle文件

```bash
cat > TimerApp/settings.gradle << 'EOF'
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TimerApp"
include ':app'
EOF
```

### 创建proguard-rules.pro文件

```bash
cat > TimerApp/app/proguard-rules.pro << 'EOF'
# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
EOF
```

## 步骤3：启动Android Studio并导入项目

1. 启动Android Studio Otter 3 Feature Drop
2. 在欢迎屏幕上，点击"Open"
3. 导航到`/home/user/vibecoding/workspace/android_timer_app_native/TimerApp`目录
4. 点击"OK"导入项目

## 步骤4：配置项目

Android Studio导入项目后，可能会提示您进行一些配置：

1. **Gradle同步**：
   - Android Studio会自动提示您同步Gradle
   - 点击"Sync Now"按钮
   - 等待同步完成（这可能需要几分钟，因为需要下载依赖项）

2. **SDK配置**：
   - 如果提示SDK版本不匹配，点击"Install missing SDK components"
   - 按照提示完成SDK安装

3. **构建工具配置**：
   - 如果提示构建工具版本问题，点击"Install Build Tools"
   - 按照提示安装所需的构建工具版本

## 步骤5：修复可能的代码问题

导入项目后，您可能需要修复一些代码问题以适应最新的Android Studio和API：

### 检查导入语句

1. 打开每个Java文件
2. 检查是否有红色波浪线标记的错误
3. 对于导入错误，将鼠标悬停在错误上，然后点击"Import class"或"Add import"

### 更新过时的API调用

1. 查找代码中标记为过时的方法（通常显示为删除线）
2. 将鼠标悬停在过时方法上，查看推荐的替代方法
3. 替换过时的方法调用

### 修复布局文件问题

如果您有XML布局文件，可能需要更新它们以适应最新的Android Studio：

1. 在Project视图中，导航到`app/src/main/res/layout`
2. 双击打开每个XML文件
3. 修复任何显示的错误

## 步骤6：构建APK

一旦项目配置正确且没有错误，您可以构建APK：

### 构建Debug APK

1. 在菜单栏中，点击"Build" > "Build Bundle(s) / APK(s)" > "Build APK(s)"
2. Android Studio将开始构建过程
3. 构建完成后，右下角会显示一个通知
4. 点击通知中的"locate"链接，找到生成的APK文件

### 构建Release APK（可选）

如果您需要发布版本的APK，需要进行签名配置：

1. 在菜单栏中，点击"Build" > "Generate Signed Bundle / APK..."
2. 选择"APK"并点击"Next"
3. 点击"Create new..."创建新的签名密钥（如果没有）
   - 填写密钥存储信息
   - 记住您设置的密码
4. 选择创建的密钥并输入密码
5. 点击"Next"
6. 选择"Release"构建变体
7. 点击"Finish"开始构建
8. 构建完成后，点击通知中的"locate"链接找到APK文件

## 步骤7：找到并验证APK

### 查找APK文件

- Debug APK通常位于：`TimerApp/app/build/outputs/apk/debug/app-debug.apk`
- Release APK通常位于：`TimerApp/app/release/app-release.apk`

### 验证APK

1. 连接Android设备到电脑
2. 在终端中运行：
   ```bash
   adb install path/to/your/app-debug.apk
   ```
3. 在设备上打开应用，测试功能是否正常

## 故障排除

### 常见问题及解决方案

1. **Gradle同步失败**
   - 确保您的网络连接正常
   - 检查`build.gradle`文件中的依赖项版本
   - 尝试点击"File" > "Invalidate Caches / Restart" > "Invalidate and Restart"

2. **构建错误**
   - 检查代码中的错误（Android Studio会在编辑器中标记）
   - 查看"Build"窗口中的详细错误信息
   - 确保所有必要的资源文件都存在

3. **缺少依赖项**
   - 在`build.gradle`文件中添加缺少的依赖项
   - 点击"Sync Now"重新同步

4. **APK安装失败**
   - 确保设备已启用USB调试
   - 检查设备是否有足够的存储空间
   - 尝试卸载旧版本的应用后再安装

## 提示和技巧

1. **使用Android Studio的内置工具**
   - 利用"Project Structure"对话框（Ctrl+Alt+Shift+S）管理依赖项和SDK版本
   - 使用"Code Inspection"（Analyze > Inspect Code）查找潜在问题

2. **优化构建速度**
   - 在`gradle.properties`文件中添加以下配置：
     ```
     org.gradle.jvmargs=-Xmx4g -XX:MaxPermSize=512m -XX:+HeapDumpOnOutOfMemoryError
     org.gradle.parallel=true
     org.gradle.configureondemand=true
     ```

3. **使用模拟器测试**
   - 如果没有物理设备，可以使用Android Studio的模拟器
   - 点击"AVD Manager"创建和管理虚拟设备

## 总结

使用Android Studio Otter 3 Feature Drop构建APK的关键步骤是：
1. 准备正确的项目结构
2. 配置构建文件
3. 在Android Studio中导入和同步项目
4. 修复任何代码问题
5. 构建并验证APK

如果您在构建过程中遇到任何问题，请参考故障排除部分或查阅Android Studio的官方文档。

祝您构建顺利！