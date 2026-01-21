# 练习计时器 - 安卓应用

这是一个功能完整的安卓分段计时器应用，支持创建多个计时器包，每个包内可设置多个自定义时长的子计时器，并在锁屏状态下持续运行并提供声音和振动提醒。

## 功能特点

- ✅ 创建多个计时器包，每个包可自定义名称
- ✅ 每个计时器包内可添加多个子计时器，精确到秒设置时长
- ✅ 支持锁屏状态下持续运行（使用前台服务）
- ✅ 子计时器结束时提供声音和振动提醒
- ✅ 自动开始下一阶段功能
- ✅ 直观的用户界面，包含环形进度条显示
- ✅ 数据本地保存，重启应用后数据不丢失

## 技术实现

- **开发语言**: Java
- **最低支持版本**: Android 5.0 (API 21)
- **目标版本**: Android 11.0 (API 30)
- **主要技术**:
  - 前台服务 (Foreground Service)
  - CountDownTimer
  - SharedPreferences + Gson 数据持久化
  - 自定义适配器 (Custom Adapter)
  - 通知系统 (Notification)

## 项目结构

```
android_timer_app_native/
├── AndroidManifest.xml       # 应用配置文件
├── MainActivity.java         # 主界面Activity
├── TimerDetailActivity.java  # 计时器详情Activity
├── TimerPack.java            # 计时器包数据模型
├── SubTimer.java             # 子计时器数据模型
├── TimerService.java         # 前台服务，确保锁屏时计时器运行
├── TimerStorage.java         # 数据存储管理
├── TimerPackAdapter.java     # 计时器包列表适配器
├── SubTimerAdapter.java      # 子计时器列表适配器
├── NotificationUtil.java     # 通知工具类
├── AddTimerDialog.java       # 添加计时器对话框
├── build.gradle              # 项目构建配置
└── res/                      # 资源文件夹
    ├── layout/               # 布局文件
    ├── values/               # 字符串、颜色、样式等资源
    ├── drawable/             # 图形资源
    └── raw/                  # 音频资源
```

## 构建和运行

### 前提条件

- Android Studio
- JDK 8 或更高版本
- Android SDK (API 21 及以上)

### 构建步骤

1. 使用 Android Studio 打开项目
2. 等待 Gradle 同步完成
3. 连接 Android 设备或启动模拟器
4. 点击 "Run" 按钮构建并运行应用

### 直接安装

如果你只想直接安装应用而不进行开发，可以使用 Android Studio 生成 APK 文件：

1. 在 Android Studio 中，选择 "Build" > "Build Bundle(s) / APK(s)" > "Build APK(s)"
2. 构建完成后，APK 文件将位于 `app/build/outputs/apk/debug/` 目录下
3. 将 APK 文件复制到你的 Android 设备上并安装

## 使用说明

1. **创建计时器**:
   - 点击主界面右上角的 "添加计时器" 按钮
   - 输入计时器名称
   - 添加所需的子计时器，设置名称和时长（精确到秒）
   - 点击 "保存" 完成创建

2. **使用计时器**:
   - 在主界面点击你创建的计时器
   - 点击 "开始" 按钮启动计时器
   - 计时器将在当前子计时器结束后自动播放提示音并振动
   - 如果启用了 "自动开始下一阶段"，将自动开始下一个子计时器

3. **管理计时器**:
   - 在主界面长按计时器可进行编辑
   - 点击计时器卡片上的 "删除" 按钮可删除计时器

## 权限说明

应用需要以下权限：

- `android.permission.VIBRATE` - 用于子计时器结束时提供振动提醒
- `android.permission.FOREGROUND_SERVICE` - 用于创建前台服务，确保锁屏时计时器继续运行

## 注意事项

- 为确保锁屏状态下计时器正常运行，请不要在系统设置中禁止应用的后台运行权限
- 如需完全退出应用，请先停止所有正在运行的计时器

## 后续优化方向

- 添加计时器模板功能
- 实现深色模式
- 添加更多提示音选择
- 支持导出/导入计时器配置
- 添加振动模式自定义