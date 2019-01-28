package com.karumi.ui.view

import android.content.Context
import com.karumi.R
import com.karumi.domain.model.AuthDomainError
import com.karumi.domain.model.DomainError
import com.karumi.domain.model.NotIndexFoundDomainError
import com.karumi.domain.model.NotInternetDomainError
import com.karumi.domain.model.UnknownDomainError

fun DomainError.asString(context: Context): String =
    context.getString(getMessage(this))

fun getMessage(domainError: DomainError): Int =
    when (domainError) {
        is NotInternetDomainError -> R.string.error_not_internet_message
        is NotIndexFoundDomainError -> R.string.error_superhero_not_found_message
        is AuthDomainError -> R.string.error_invalid_credentials
        is UnknownDomainError -> R.string.error_unknown_message
    }

