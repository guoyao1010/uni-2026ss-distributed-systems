package dsg.rmi.socialmedia;


public class DSGSocialMediaRMIClient extends DSGShell{

    private DSGSocialMedia socialMedia;

    // #############################
    // # INITIALIZATION & SHUTDOWN #
    // #############################

    public void init(String registryHost, int registryPort) {
        /*
         * TODO: Implement client startup code
         */
    }


    public void shutdown() {
        /*
         * TODO: Implement client shutdown code
         */
    }


    // ##################
    // # CLIENT METHODS #
    // ##################



    // #########
    // # SHELL #
    // #########

    @Override
    protected boolean processCommand(String[] args) {
        /*
         * TODO: Parse shell commands
         */
        return true;
    }


    // ########
    // # MAIN #
    // ########

    public static void main(String[] args) {
        /*
         * TODO: Start the client
         */
    }
}
