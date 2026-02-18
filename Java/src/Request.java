package src;

/** Abstract class to represent a request between a server and client.
 */
public abstract class Request {
    private RequestType request;

    /** Creates a request using the RequestType as parameter.
     * @param request Type of request.
     */
    public Request(RequestType request){
        this.request = request;
    }

    /** Returns the type of request.
     * @return RequestType of the object.
     */
    public RequestType GetRequest(){
        return request;
    }
}
