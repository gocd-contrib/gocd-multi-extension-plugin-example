# Example multi-extension plugin for GoCD 18.3 and beyond

Since [GoCD 18.3](https://www.gocd.org/download/), there is support for plugins with multiple extensions in them. All existing single-extension plugins will still work.

There are two points to remember about multi-extension plugins:

1. It's not possible to have two extensions of the same type in the same plugin. For instance, you cannot have two "task" extensions.

2. Only one extension can respond to the configuration set of requests.


This example plugin contains three extensions:

* Analytics
* Notification
* Task

Only the analytics extension responds to configuration requests in this plugin. The notification extension responds with a 400 status.
