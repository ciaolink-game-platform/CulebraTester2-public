/**
* CulebraTester
* ## Snaky Android Test --- If you want to be able to try out the API using the **Execute** or **TRY** button from this page - an android device should be connected using `adb` - the server should have been started using `./culebratester2 start-server`  then you will be able to invoke the API and see the responses. 
*
* OpenAPI spec version: 2.0.57
* 
*
* NOTE: This class is auto generated by the swagger code generator program.
* https://github.com/swagger-api/swagger-codegen.git
* Do not edit the class manually.
*/package io.swagger.server.models


/**
 * An object reference. This is a reference to an object that has been stored in the **Object Store**. The object reference can be later used by APIs requiring an object. * @param oid the object ID * @param className the class name*/
data class ObjectRef (    /* the object ID */
    val oid: kotlin.Int,    /* the class name */
    val className: kotlin.String
) {
}
