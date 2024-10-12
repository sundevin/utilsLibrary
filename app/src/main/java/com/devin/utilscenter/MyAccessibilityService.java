package com.devin.utilscenter;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.LinkedList;
import java.util.List;

public class MyAccessibilityService extends AccessibilityService {



    public static final int STEP_START = 0;

    public static final int STEP_SWITCH_CONTACT_TAB = 1;
    public static final int STEP_FIND_CONTACT = 2;

    private static final int STEP_SCROLL_LIST = 3;
    private static final int STEP_CLICK_CONTACT = 4;
    private static final int STEP_OPEN_DIALOG = 5;

    public static int step = STEP_START;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        Log.e("onAccessibilityEvent", "current step:"+step);

        int eventType = event.getEventType();

        CharSequence packageName = event.getPackageName();
        if (TextUtils.equals(packageName, "com.tencent.mm") &&
                (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)
        ) {
            if (step == STEP_START) {
                boolean b = navigateToContactsTab();
                if (b) {
                    step = STEP_SWITCH_CONTACT_TAB;
                    sleepWait(1000);
                }
            } else if (step == STEP_SWITCH_CONTACT_TAB || step == STEP_SCROLL_LIST) {

                AccessibilityNodeInfo nodeInfo = getNodeByText(getRootInActiveWindow(), "哇哇哇");
                if (nodeInfo != null) {
                    for (int i = 0; i < 6; i++) {
                        if (nodeInfo != null) {
                            nodeInfo = nodeInfo.getParent();
                        }
                    }
                    if (nodeInfo != null) {
                        boolean result = nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        if (result) {
                            step = STEP_CLICK_CONTACT;
                            sleepWait(1000);
                            Log.e("onAccessibilityEvent", "找到了联系人 并进行了点击:");
                        }
                    }
                } else {
                    AccessibilityNodeInfo contactListView = getContactListView();
                    if (contactListView != null) {
                        contactListView.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
                        step = STEP_SCROLL_LIST;
                    } else {
                        Log.e("onAccessibilityEvent", "未找到listView:");
                    }

                }
            } else if (step == STEP_CLICK_CONTACT) {
                AccessibilityNodeInfo nodeInfo = getNodeByText(getRootInActiveWindow(), "音视频通话");
                if (nodeInfo != null) {
                    for (int i = 0; i < 2; i++) {
                        if (nodeInfo != null) {
                        nodeInfo = nodeInfo.getParent();
                        }
                    }

                    if (nodeInfo != null) {


                        CharSequence className = nodeInfo.getClassName();
                        String viewIdResourceName = nodeInfo.getViewIdResourceName();
                        boolean isClickable = nodeInfo.isClickable();
                        boolean visibleToUser = nodeInfo.isVisibleToUser();
                        boolean isFocusable = nodeInfo.isFocusable();
                        boolean isFocused = nodeInfo.isFocused();
                        Log.e("onAccessibilityEvent", "className:" + className
                                + " viewIdResourceName:" + viewIdResourceName
                                + " isClickable:" + isClickable
                                + " visibleToUser:" + visibleToUser
                                + " isFocusable:" + isFocusable
                                                   + " isFocused:" + isFocused);

                        AccessibilityNodeInfo finalNodeInfo = nodeInfo;
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            Log.e("onAccessibilityEvent", "222222className:" + className
                                    + " viewIdResourceName:" + viewIdResourceName
                                    + " isClickable:" + isClickable
                                    + " visibleToUser:" + visibleToUser
                                    + " isFocusable:" + isFocusable
                                    + " isFocused:" + isFocused);                        boolean result = finalNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            if (result) {
                                Log.e("onAccessibilityEvent", "音视频通话");
                                step = STEP_OPEN_DIALOG;
                                sleepWait(1000);
                            } else {
                                Log.e("onAccessibilityEvent", "音视频通话 点击失败");
                            }
                        }, 500);  // 延时500毫秒



                    } else {
                        Log.e("onAccessibilityEvent", "未找到音视频通话按钮");
                    }
                } else {
                    Log.e("onAccessibilityEvent", "未找到音视频通话按钮2");
                }

            }else if (step == STEP_OPEN_DIALOG) {
                AccessibilityNodeInfo nodeInfo = getNodeByText(getRootInActiveWindow(), "语音通话");
                if (nodeInfo != null) {
                    for (int i = 0; i < 3; i++) {
                        nodeInfo = nodeInfo.getParent();
                    }

                    if (nodeInfo != null) {
                        boolean result = nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        if (result) {
                            Log.e("onAccessibilityEvent", "语音通话");
                            step = STEP_OPEN_DIALOG;
                        }
                    } else {
                        Log.e("onAccessibilityEvent", "未找到语音通话按钮");
                    }
                }
            }
        }

//        Log.e("MyAccessibilityService", "onAccessibilityEvent:" + event.toString());
        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED:
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED:
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:
                break;

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
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode == null) return false;

        // 查找 "通讯录" tab 可能包含的文本
        List<AccessibilityNodeInfo> nodes = rootNode.findAccessibilityNodeInfosByText("通讯录");

        // 遍历找到并点击它
        for (AccessibilityNodeInfo node : nodes) {
            node = node.getParent().getParent();
            if (node != null && node.isClickable()) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                return true;
            }
        }
        return false;
    }



    private AccessibilityNodeInfo getNodeByText(AccessibilityNodeInfo rootNode, String text) {

        if (rootNode != null) {
            List<AccessibilityNodeInfo> list = rootNode.findAccessibilityNodeInfosByText(text);
            if (list.isEmpty()) {
                return null;
            } else {
                return list.get(0);
            }
        }
        return null;
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
