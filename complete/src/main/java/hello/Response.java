package hello;

/**
 * Response
 * need getter&setter can convert to JSON(ResponseBody)
 */

public class Response {

    private String description = "";

    public Response(String description){
        this.description = description;
    }

    public String getdescription() {
		return description;
	}

	public void setdescriptiond(String description) {
		this.description = description;
	}

    @Override
    public String toString(){
        return description;
    }
}