# 如何生成APK文件

由于直接在当前环境中无法完整构建Android APK（需要Android SDK和构建工具），这里提供两种方法来生成可安装的APK文件：

## 方法一：使用Android Studio（推荐）

### 前提条件
- 安装最新版本的[Android Studio](https://developer.android.com/studio)
- 确保已安装Android SDK和必要的构建工具

### 步骤
1. **打开项目**
   - 启动Android Studio
   - 点击 "Open an existing project"
   - 导航到下载的项目文件夹并选择

2. **配置项目**
   - 等待Gradle同步完成
   - 如果出现SDK版本不匹配的错误，按照提示更新build.gradle文件中的版本号

3. **生成APK**
   - 选择 "Build" > "Build Bundle(s) / APK(s)" > "Build APK(s)"
   - 构建完成后，会显示一个通知，点击 "locate" 查看APK文件
   - APK文件将位于 `app/build/outputs/apk/debug/app-debug.apk`

4. **安装APK**
   - 将APK文件复制到您的Android设备
   - 在设备上找到APK文件并点击安装
   - 如果是首次安装来自未知来源的应用，需要在设置中允许安装

## 方法二：使用命令行

### 前提条件
- 安装[Android SDK Command-line Tools](https://developer.android.com/studio#command-tools)
- 设置ANDROID_HOME环境变量指向SDK安装目录
- 安装Java JDK 8或更高版本

### 步骤
1. **导航到项目目录**
   ```bash
   cd /path/to/android_timer_app_native
   ```

2. **构建项目**
   ```bash
   ./gradlew assembleDebug
   ```
   或者在Windows上：
   ```cmd
   gradlew assembleDebug
   ```

3. **查找APK文件**
   - 构建完成后，APK文件将位于 `app/build/outputs/apk/debug/app-debug.apk`

## 方法三：使用在线构建服务

如果您没有安装Android开发环境，可以考虑使用在线构建服务：

1. **GitHub Actions**
   - 将项目上传到GitHub仓库
   - 创建一个简单的workflow文件来构建APK
   - 构建完成后，您可以从Actions标签页下载APK

2. **其他在线构建服务**
   - [AppCenter](https://appcenter.ms/)
   - [CircleCI](https://circleci.com/)
   - [Travis CI](https://travis-ci.org/)

## 测试APK

安装APK后，您可以验证应用是否正常工作：

1. 打开应用
2. 创建一个新的计时器
3. 添加几个子计时器
4. 启动计时器并测试功能
5. 测试锁屏状态下计时器是否继续运行
6. 测试提示音和振动功能

## 故障排除

### 常见问题
1. **构建失败**
   - 确保已安装正确版本的Android SDK
   - 检查build.gradle文件中的依赖项版本

2. **安装失败**
   - 确保您的设备允许安装来自未知来源的应用
   - 检查APK是否与您的设备架构兼容（arm64-v8a, armeabi-v7a等）

3. **应用崩溃**
   - 检查设备日志以获取错误信息
   - 确保您的设备运行的Android版本与应用兼容（最低API 21）

如需进一步帮助，请参考[Android开发者文档](https://developer.android.com/studio/build/building-cmdline)或在Stack Overflow上寻求帮助。