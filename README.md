# Example multi-extension plugin for GoCD 18.3 and beyond

Since [GoCD 18.3](https://www.gocd.org/download/), there is support for plugins with multiple extensions in them. All existing single-extension plugins will still work.

There are two points to remember about multi-extension plugins:

1. It's not possible to have two extensions of the same type in the same plugin. For instance, you cannot have two "task" extensions.

2. Only one extension can respond to the configuration set of requests.


This example plugin implements three extensions:

* [Analytics](https://github.com/arvindsv/gocd-multi-extension-plugin-example/blob/b7bd2bcd8080b1d496621b157e97d826c456ee3b/src/main/java/com/thoughtworks/go/analytics/AnalyticsPlugin.java#L39)
* [Notification](https://github.com/arvindsv/gocd-multi-extension-plugin-example/blob/b7bd2bcd8080b1d496621b157e97d826c456ee3b/src/main/java/com/thoughtworks/go/notification/NotificationPlugin.java#L36)
* [Task](https://github.com/arvindsv/gocd-multi-extension-plugin-example/blob/b7bd2bcd8080b1d496621b157e97d826c456ee3b/src/main/java/com/thoughtworks/go/task/TaskPlugin.java#L35)

Only the analytics extension [responds to configuration requests](https://github.com/arvindsv/gocd-multi-extension-plugin-example/blob/b7bd2bcd8080b1d496621b157e97d826c456ee3b/src/main/java/com/thoughtworks/go/analytics/AnalyticsPlugin.java#L54-L62) in this plugin. The notification extension [responds with a 400 status](https://github.com/arvindsv/gocd-multi-extension-plugin-example/blob/b7bd2bcd8080b1d496621b157e97d826c456ee3b/src/main/java/com/thoughtworks/go/notification/NotificationPlugin.java#L52-L60).
