package com.devin.utilscenter;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class MyAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        CharSequence packageName = event.getPackageName();
        if (TextUtils.equals(packageName, "com.tencent.mm")) {
            navigateToContactsTab();
        }

        Log.e("MyAccessibilityService", "onAccessibilityEvent:" + event.toString());
        switch (event.getEventType()) {
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


    private AccessibilityNodeInfo getNodeByText( String text) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            return rootNode.findAccessibilityNodeInfosByText(text).get(0);
        }
        return null;
    }


}
