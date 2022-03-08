package com.frommetoyou.modulesapp2.domain.usecases

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

const val MAIN_DYNAMIC_URL_PREFIX = "https://modulesapp2.page.link"

/**
 * Esta clase se encarga de generar un dynamic link
 *
 * @constructor Se le inyecta un Application Context que será utilizada por los demás métodos de la
 * clase
 * */
class GenerateLinkUseCase @Inject constructor(
    @ApplicationContext val context: Context,
) {
    /**
     * Esta fun está encargada de generar un dynamic link que transporta data, específicamente
     * cuál es el id del botón seleccionado por el usuario al momento de crearla.
     *
     * @param btnSelected Es el ID del btn seleccionado, necesario para crear el link y transportar
     * el mensaje que será leido luego por GetDynamicLinkDataUseCase
     *
     * @param activity La actividad de donde fue lanzada, para mostrar el bottom sheet para compartir
     * el mensaje por distintos medios (wsp, msg, mail, etc)
     *
     * @return Se retorna el String del link
     *
     * @since 1.0.0
     * @author F.G.M
     * */
    fun generateSelectedBtnLink(btnSelected: String, activity: Activity) {
        val dynamicLink =
            Firebase.dynamicLinks.dynamicLink { // or Firebase.dynamicLinks.shortLinkAsync
                link = Uri.parse("$MAIN_DYNAMIC_URL_PREFIX?btn=btnSelected")
                domainUriPrefix = MAIN_DYNAMIC_URL_PREFIX
                androidParameters(context.packageName) {
                    minimumVersion = 1
                }
                socialMetaTagParameters {
                    title = "Modules App Dynamic Link"
                    description = "This link works whether the app is installed or not!"
                    imageUrl = Uri.parse("https://www.google.com/url?sa=i&url=https%3A%2F%2Fes.vexels.com%2Fpng-svg%2Fvista-previa%2F139556%2Flogotipo-de-android&psig=AOvVaw25U_jr9x1m2ZGYjXISlP40&ust=1643941174440000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCKikuqC84vUCFQAAAAAdAAAAABAO")
                }
            }
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, dynamicLink.uri)
        intent.type = "text/plain"

        val shareIntent = Intent.createChooser(intent, null)
        activity.startActivity(shareIntent)
    }
}
