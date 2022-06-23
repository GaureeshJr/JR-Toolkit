package com.jrtk.editor;

import com.jrtk.client.Application;

public abstract class editorWindow {
    protected Application application;
    public boolean enabled = true;

    public editorWindow(Application a)
    {
        this.application = a;
    }

    public abstract void Show();

}
