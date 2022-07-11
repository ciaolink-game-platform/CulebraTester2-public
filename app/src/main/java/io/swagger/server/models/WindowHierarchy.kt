/**
* CulebraTester
* ## Snaky Android Test --- If you want to be able to try out the API using the **Execute** or **TRY** button from this page - an android device should be connected using `adb` - the server should have been started using `./culebratester2 start-server`  then you will be able to invoke the API and see the responses. 
*
* OpenAPI spec version: 2.0.43
* 
*
* NOTE: This class is auto generated by the swagger code generator program.
* https://github.com/swagger-api/swagger-codegen.git
* Do not edit the class manually.
*/package io.swagger.server.models

import io.swagger.server.models.WindowHierarchyChild

/**
 *  * @param id  * @param text  * @param timestamp  * @param children */
data class WindowHierarchy (    val id: kotlin.String? = null,    val text: kotlin.String? = null,    val timestamp: kotlin.String? = null,    val children: kotlin.Array<WindowHierarchyChild>? = null
) {
}
