package com.devin.utilscenter;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.devin.util.ToastUtils;

import java.util.LinkedList;
import java.util.List;

public class MyAccessibilityService extends AccessibilityService {


    public static final int STEP_START = 0;

    public static final int STEP_SWITCH_CONTACT_TAB = 1;
    public static final int STEP_FIND_CONTACT = 2;

    private static final int STEP_SCROLL_LIST = 3;
    private static final int STEP_CLICK_CONTACT = 4;
    private static final int STEP_OPEN_DIALOG = 5;

    public static int currentStep = STEP_START;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        Log.e("onAccessibilityEvent", "current step:" + currentStep);

        int eventType = event.getEventType();

        CharSequence packageName = event.getPackageName();
        if (TextUtils.equals(packageName, "com.tencent.mm")
                && (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                || eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)) {
            if (currentStep == STEP_START) {
                boolean b = navigateToContactsTab();
                if (b) {
                    currentStep = STEP_SWITCH_CONTACT_TAB;
                    sleepWait(1000);
                }
            } else if (currentStep == STEP_SWITCH_CONTACT_TAB) {
                boolean targetContact = clickTargetContact("Devin");
                if (targetContact) {
                    currentStep = STEP_CLICK_CONTACT;
                    sleepWait(1000);
                    Log.e("onAccessibilityEvent", "找到了联系人 并进行了点击:");
                } else {
                    boolean scrolled = scrollContactList();
                    if (!scrolled) {
                        ToastUtils.show("未找到联系人");
                        // TODO: 2024/10/13  
                    }
                }
            } else if (currentStep == STEP_CLICK_CONTACT) {

                boolean openedVideoDialog = openVideoDialog();
                if (openedVideoDialog) {
                    Log.e("onAccessibilityEvent", "【音视频通话】按钮点击成功");
                    currentStep = STEP_OPEN_DIALOG;
                    sleepWait(500);
                } else {
                    Log.e("onAccessibilityEvent", "尝试点击【音视频通话按钮】失败");
                }

            } else if (currentStep == STEP_OPEN_DIALOG) {
                boolean openedVideoDialog = clickDialogMenu(1);
                if (openedVideoDialog) {
                    Log.e("onAccessibilityEvent", "弹窗按钮点击成功，流程结束");
                    currentStep = STEP_OPEN_DIALOG;
                } else {
                    Log.e("onAccessibilityEvent", "弹窗按钮点击失败");
                }
            }
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
