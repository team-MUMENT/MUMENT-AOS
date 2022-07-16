package com.mument_android.app.presentation.ui.customview

class MumentDialogBuilder {
    private var header: String? = null
    private var body: String? = null
    private var allowListener: (() -> Unit)? = null
    private var cancelListener: (() -> Unit)? = null

    fun build(): MumentDialog {
        return MumentDialog(header, body, allowListener, cancelListener)
    }

    fun setHeader(header: String): MumentDialogBuilder {
        this.header = header
        return this
    }

    fun setBody(body: String): MumentDialogBuilder {
        this.body = body
        return this
    }

    fun setAllowListener(listener:() -> Unit): MumentDialogBuilder{
        allowListener = listener
        return this
    }

    fun setCancelListener(listener: () -> Unit): MumentDialogBuilder {
        cancelListener = listener
        return this
    }
}