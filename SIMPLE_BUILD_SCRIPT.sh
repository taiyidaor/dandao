#!/bin/bash

# 简单的Android APK构建脚本
echo "=== 开始构建Android APK ==="

# 检查Java是否安装
if ! command -v java &> /dev/null; then
    echo "错误：未找到Java。请安装Java 8或更高版本。"
    exit 1
fi

echo "Java版本："
java -version

# 检查是否有系统Gradle
if ! command -v gradle &> /dev/null; then
    echo "错误：未找到Gradle。请安装Gradle或使用Android Studio。"
    exit 1
fi

echo "Gradle版本："
gradle --version

# 创建必要的目录结构
echo "创建项目目录结构..."
mkdir -p app/src/main/java/com/example/timerapp
mkdir -p app/src/main/res/layout
mkdir -p app/src/main/res/values
mkdir -p app/build/outputs/apk/debug

# 复制Java源文件到正确位置
echo "复制源文件..."
cp MainActivity.java app/src/main/java/com/example/timerapp/
cp TimerDetailActivity.java app/src/main/java/com/example/timerapp/
cp AddTimerDialog.java app/src/main/java/com/example/timerapp/
cp TimerService.java app/src/main/java/com/example/timerapp/
cp NotificationUtil.java app/src/main/java/com/example/timerapp/
cp TimerStorage.java app/src/main/java/com/example/timerapp/
cp TimerPack.java app/src/main/java/com/example/timerapp/
cp SubTimer.java app/src/main/java/com/example/timerapp/
cp TimerPackAdapter.java app/src/main/java/com/example/timerapp/
cp SubTimerAdapter.java app/src/main/java/com/example/timerapp/

# 复制AndroidManifest.xml
cp AndroidManifest.xml app/src/main/

# 复制res目录
cp -r res/* app/src/main/res/

# 创建简单的app/build.gradle文件
cat > app/build.gradle << 'EOF'
apply plugin: 'com.android.application'

android {
    compileSdkVersion 33
    buildToolsVersion "33.0.0"

    defaultConfig {
        applicationId "com.example.timerapp"
        minSdkVersion 21
        targetSdkVersion 33
        versionCode 1
        versionName "1.0"
    }

    buildTypes {
        debug {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'androidx.recyclerview:recyclerview:1.2.1'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'com.google.android.material:material:1.5.0'
}
EOF

# 创建简单的项目build.gradle文件
cat > app/settings.gradle << 'EOF'
include ':app'
EOF

# 执行构建
echo "开始构建APK..."
cd app
gradle assembleDebug

# 检查构建结果
if [ -f "build/outputs/apk/debug/app-debug.apk" ]; then
    echo "=== 构建成功！==="
    echo "APK文件位置：app/build/outputs/apk/debug/app-debug.apk"
else
    echo "=== 构建失败！==="
    echo "请检查错误信息。"
    exit 1
fi