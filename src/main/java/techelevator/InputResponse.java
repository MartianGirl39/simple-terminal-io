package techelevator;

class InputResponse {
    private final InputHandler onEnter;
    private final boolean redirectOnValid;

    private Response response = null;

    InputResponse(InputHandler onEnter, boolean redirectOnValid){
        this.onEnter = onEnter;
        this.redirectOnValid = redirectOnValid;
    }

    InputResponse(InputHandler onEnter, boolean redirectOnValid, Response response){
        this(onEnter, redirectOnValid);
        this.response = response;
    }

    public final InputHandler getOnEnter() {
        return onEnter;
    }

    public final boolean isRedirectOnValid() {
        return redirectOnValid;
    }

    public Response getResponse() {
        return response;
    }
}
