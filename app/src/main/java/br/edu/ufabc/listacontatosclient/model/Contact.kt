package br.edu.ufabc.listacontatosclient.model

/**
 * Transfer (domain) object.
 * @property id the id
 * @property name the name
 * @property email the email
 * @property phone the phone
 * @property address the address
 */
data class Contact(
    val id: Long,
    val name: String,
    val email: String,
    val phone: String,
    val address: String
)
