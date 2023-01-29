package com.mument_android.core_dependent.ui

class MumentDialogBuilder {
    private var header: String? = null
    private var body: String? = null
    private var allowListener: (() -> Unit)? = null
    private var allowButtonText: String? = null
    private var cancelListener: (() -> Unit)? = null
    private var cancelButtonText: String? = null

    fun build(): MumentDialog {
        return MumentDialog(
            header = header,
            body = body,
            allowButtonText = allowButtonText,
            allowListener = allowListener,
            cancelListener = cancelListener,
            cancelButtonText = cancelButtonText
        )
    }

    fun setHeader(header: String): MumentDialogBuilder {
        this.header = header
        return this
    }

    fun setBody(body: String): MumentDialogBuilder {
        this.body = body
        return this
    }

    fun setAllowListener(text: String? = null, listener:() -> Unit): MumentDialogBuilder {
        allowButtonText = text
        allowListener = listener
        return this
    }

    fun setCancelListener(text: String? = null, listener: () -> Unit): MumentDialogBuilder {
        cancelButtonText = text
        cancelListener = listener
        return this
    }

}