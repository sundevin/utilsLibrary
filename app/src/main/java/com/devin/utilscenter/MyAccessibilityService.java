package com.devin.utilscenter;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.LinkedList;
import java.util.List;

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "MyAccessibilityService";


    public static final int STEP_START = 0;

    public static final int STEP_SWITCH_CONTACT_TAB = 1;
    public static final int STEP_FIND_CONTACT = 2;

    private static final int STEP_SCROLL_LIST = 3;
    private static final int STEP_CLICK_CONTACT = 4;
    private static final int STEP_OPEN_DIALOG = 5;

    public static int currentStep = STEP_START;

    public static final String wechatPackageName = "com.tencent.mm";
    private boolean startDelay;
    private long lastExecTimeStamp;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        Log.d(TAG, "eventType: " + eventType + "\t PackageName:" + event.getPackageName());
        Log.d(TAG, "&& event.getSource(): " + event.getSource());
        if ((eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED
                || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) && event.getSource() != null && event.getPackageName() != null) {
            CharSequence packageName = event.getPackageName();
            Log.d(TAG, "packageName=: " + packageName);
//            if (!wechatPackageName.equals(packageName)) {
//                return;
//            }
            if (this.startDelay && System.currentTimeMillis() - this.lastExecTimeStamp < 300L) {
                Log.d(TAG, "已经接听了没有比较再接听一次了 : " + packageName);
                return;
            }
            this.lastExecTimeStamp = System.currentTimeMillis();
            this.startDelay = false;
            CharSequence className = event.getClassName();
            if (className != null) {
                Log.d(TAG, "页面->: " + className);
            }
            autoConnectWeChatCall(event);
        } else if (eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED && event.getPackageName() != null) {
            Log.d(TAG, "通知栏发生变化了...");
            Log.d(TAG, "eventType: " + eventType + "\t PackageName:" + event.getPackageName());
            CharSequence className = event.getClassName();
            if (className != null) {
                Log.d(TAG, "页面->: " + className);
            }
            if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
                if ("com.tencent.mm".equals(event.getPackageName())) {
                    Notification notification = (Notification) event.getParcelableData();
                    PendingIntent pendingIntent1 = notification.contentIntent;
                    Log.d(TAG, "pendingIntent..." + pendingIntent1);
                    try {
                        String title = notification.extras.getString(Notification.EXTRA_TITLE);
                        String content = notification.extras.getString(Notification.EXTRA_TEXT);
                        Log.d(TAG, "title..." + title + "\tcontent :" + content);
                        if (pendingIntent1 != null) {
                            pendingIntent1.send();
                        }

                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    private void autoConnectWeChatCall(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();
        int type = 1;
        Log.d(TAG, "event.getClassName()..." + event.getClassName() + "\t");
        if (source != null) {
            //微信聊天页面,是一个listView,寻找红包记录
//            List<AccessibilityNodeInfo> listAccessibilityNodes = source.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bp0");
//            if (listAccessibilityNodes != null) {
//                Log.d(TAG, "是否获取到聊天的list列表数据->  " + listAccessibilityNodes.size());
//                if (listAccessibilityNodes.size() >= 1) {
//                    Log.d(TAG, " hBaccessibilityNodeInfos.get(0).getChildCount()::" + listAccessibilityNodes.get(0).getChildCount());
//                    //获取到recyclerView节点数据
//                    AccessibilityNodeInfo accessibilityNodeInfo = listAccessibilityNodes.get(0); //列表
//                    //检索聊天记录上所有的红包记录
//                    List<AccessibilityNodeInfo> list = accessibilityNodeInfo.findAccessibilityNodeInfosByText("微信红包");
//
//                    Log.d(TAG, "总共有几个红包 :" + list.size());
//                    //默认点击最后一个红包
//                    tap(this, list.get(list.size() - 1));
//                }
//            }

            //开始拆红包页面
//            List<AccessibilityNodeInfo> accessibilityNodeInfosByViewId = source.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/j6h");
//            if (accessibilityNodeInfosByViewId != null) {
//                if (accessibilityNodeInfosByViewId.size() > 0) {
//
//
//                    //红包开的按钮
//                    AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfosByViewId.get(0);
//                    tap(this, accessibilityNodeInfo);
//                    Log.d(TAG, "开始点击红包了:");
//                }
//            }

            //微信通知栏上拒绝、接听页面
            List<AccessibilityNodeInfo> accessibilityNodeInfos = source.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/kg6");
            Log.d(TAG, "autoConnectWeChatCall..." + accessibilityNodeInfos.size() + "\t ");
            if (accessibilityNodeInfos.size() >= 1 && accessibilityNodeInfos.get(0).getChildCount() == 0) {
                String text = accessibilityNodeInfos.get(0).getText().toString();
                Log.d(TAG, "autoConnectWeChatCall... text :" + text);
                if (text.equals("邀请你视频通话")) {
                    tap(this, accessibilityNodeInfos.get(0));
                    Log.d(TAG, "邀请你视频通话...");
                } else if (text.equals("邀请你语音通话")) {
                    tap(this, accessibilityNodeInfos.get(0));
                    Log.d(TAG, "邀请你语音通话...");

                }
            }
        }

        if (event.getClassName() == null && this.getRootInActiveWindow() != null) {
            Log.d(TAG, "微信窗口自动变化");
            List<AccessibilityNodeInfo> crdList = this.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.tencent.mm:id/crd");
            if ((crdList != null) && !crdList.isEmpty()) {
                AccessibilityNodeInfo var9 = crdList.get(0); //crd

                if (var9 != null) {
                    var9 = var9.getChild(0); //frameLayout
                    if (var9 != null) {
                        var9 = var9.getChild(0); //frameLayout

                        if (var9 != null) {
                            var9 = var9.getChild(0); //View

                            if (var9 != null) {
                                var9 = var9.getChild(0);  //View

                                if (var9 != null) {
                                    var9 = var9.getChild(0); //View

                                    if (var9 != null) {
                                        final AccessibilityNodeInfo var4 = var9.getChild(0); //View  最终接听电话的页面布局
                                        if (var4 != null && var4.getChildCount() >= 7) {
                                            CharSequence var13 = var4.getChild(0).getContentDescription();
                                            if (var13 == null) {
                                                var13 = (CharSequence) "";
                                            } else {
                                                Log.d(TAG, "getChild(0).contentDescription...");
                                            }

                                            String var13trim = var13.toString().trim();

                                            Log.d(TAG, "var13trim==" + var13trim);

                                            String[] result = var13trim.split(" ");
                                            Log.d(TAG, "result length" + result.length);

                                            if (result.length >= 2) {
                                                String wechatName = result[0];
                                                String wechatType = result[1];
                                                Log.d(TAG, "wechatName" + wechatName + "\t wechatType" + wechatType);
                                                if (!wechatType.contains("视频通话")) {
                                                    if (wechatType.contains("语音通话")) {
                                                        type = 0;
                                                    } else {
                                                        type = -1;
                                                    }
                                                }
                                                if (type == -1) {
                                                    Log.d(TAG, "wechatType 是-1 ");
                                                } else {
                                                    Log.d(TAG, "接听微信电话和视频 ");
                                                    if (type == 0) {
                                                        tapConnectBtnOrSpreadBtn(var4, this);
                                                    } else {
                                                        tapConnectBtnOrSpreadBtn(var4, this);
                                                    }


                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

        }
    }

    private void tapConnectBtnOrSpreadBtn(AccessibilityNodeInfo var4, final MyAccessibilityService service) {
        final AccessibilityNodeInfo child = var4.getChild(var4.getChildCount() - 1);
        if (child != null) {
            CharSequence contentDescription = child.getContentDescription();
            String contentDescriptionTrim = "";
            if (contentDescription != null) {
                contentDescriptionTrim = contentDescription.toString().trim();
                Log.i(TAG, "contentDescriptionTrim:::" + contentDescriptionTrim);
            }

            if ("接听".equals(contentDescriptionTrim)) {
                Log.i(TAG, "接听:::");
                tap(service, child);
                this.startDelay = true;
            } else if ("扬声器已关".equals(contentDescriptionTrim)) {
                Log.i(TAG, "开启扬声器:::");
                tap(service, child);
                this.startDelay = true;
            }
        }
    }




    //=======================================================

    public static final void tap(AccessibilityService var0, AccessibilityNodeInfo var1) {
        if (var1 != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Log.i(TAG, "开始  tap....." + Thread.currentThread().getName());
                Rect rect = new Rect();
                var1.getBoundsInScreen(rect);
                GestureDescription.Builder gestureBuilder = new GestureDescription.Builder();
                Path path = new Path();
                Log.i(TAG, "rect.centerX()：：" + rect.centerX() + "\t rect.centerY()::" + rect.centerY());
                Log.i(TAG, "rect.left：：" + rect.left + "\t rect.right()::" + rect.right + "\t rect.top()::" + rect.top + "\t rect.bottom()::" + rect.bottom);
                path.moveTo(rect.centerX(), rect.centerY());
                gestureBuilder.addStroke(new GestureDescription.StrokeDescription(path, 0L, 200L));
                GestureDescription gestureDescription = gestureBuilder.build();
                var0.dispatchGesture(gestureDescription, new GestureResultCallback() {
                    @Override
                    public void onCompleted(GestureDescription gestureDescription) {
                        super.onCompleted(gestureDescription);
                        Log.i(TAG, "onCompleted：：");
                    }

                    @Override
                    public void onCancelled(GestureDescription gestureDescription) {
                        super.onCancelled(gestureDescription);
                        Log.i(TAG, "onCancelled：：");
                    }
                }, null);
            } else {
                Log.i(TAG, "收拾只能在7.0以上使用了");
            }
        } else {
            Log.i(TAG, "var1 是空的.....");
        }
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.e("MyAccessibilityService", "onServiceConnected:");
    }

    @Override
    public void onInterrupt() {
        Log.e("MyAccessibilityService", "onInterrupt:");
    }






    private void sleepWait(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 找到"通讯录"tab并点击
     *
     * @return
     */
    private boolean navigateToContactsTab() {
        AccessibilityNodeInfo nodeInfo = findTargetNodeByText("通讯录", 2);
        if (nodeInfo != null && nodeInfo.isClickable()) {
            return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            Log.e("onAccessibilityEvent", "未找到通讯录tab");
        }
        return false;
    }

    private boolean clickTargetContact(String name) {
        AccessibilityNodeInfo nodeInfo = findTargetNodeByText(name, 6);
        if (nodeInfo != null && nodeInfo.isClickable()) {
            return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            Log.e("onAccessibilityEvent", "未找到【" + name + "】节点");
        }
        return false;
    }

    private boolean scrollContactList() {
        AccessibilityNodeInfo contactListView = getContactListView();
        if (contactListView != null) {
            return contactListView.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
        } else {
            Log.e("onAccessibilityEvent", "未找到通讯录列表");
        }

        return false;
    }

    private boolean openVideoDialog() {
        AccessibilityNodeInfo nodeInfo = findTargetNodeByText("音视频通话", 2);
        if (nodeInfo != null && nodeInfo.isClickable()) {
            return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            Log.e("onAccessibilityEvent", "未找到【音视频通话】节点");
        }
        return false;
    }

    private boolean clickDialogMenu(int type) {
        String text = type == 1 ? "语音通话" : "视频通话";
        AccessibilityNodeInfo nodeInfo = findTargetNodeByText(text, 3);
        if (nodeInfo != null && nodeInfo.isClickable()) {
            return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            Log.e("onAccessibilityEvent", "未找到【" + text + "】节点");
        }
        return false;
    }


    /**
     * 查找文本对应的可点击的节点
     *
     * @param text
     * @param parentLevel
     * @return
     */
    private AccessibilityNodeInfo findTargetNodeByText(String text, int parentLevel) {

        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return null;
        }

        List<AccessibilityNodeInfo> nodeInfoList =rootInActiveWindow.findAccessibilityNodeInfosByText(text);

        AccessibilityNodeInfo targetNode = null;
        if (!nodeInfoList.isEmpty()) {
            targetNode = nodeInfoList.get(0);
        }

        if (targetNode != null) {
            for (int i = 0; i < parentLevel; i++) {
                if (targetNode != null) {
                    targetNode = targetNode.getParent();
                }
            }
        }
        return targetNode;
    }


    private AccessibilityNodeInfo getContactListView() {
        LinkedList<AccessibilityNodeInfo> linkedList = new LinkedList<>();
        linkedList.offer(getRootInActiveWindow());

        AccessibilityNodeInfo nodeInfo = null;

        while (!linkedList.isEmpty()) {
            nodeInfo = linkedList.poll();
            if (nodeInfo == null) {
                continue;
            }
            if (TextUtils.equals(nodeInfo.getClassName(), "androidx.recyclerview.widget.RecyclerView") && nodeInfo.isScrollable()) {
                return nodeInfo;
            }

            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
                linkedList.offer(nodeInfo.getChild(i));
            }
        }

        return null;
    }

}
