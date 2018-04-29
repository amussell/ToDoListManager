/**
 * A class used to store credentials for an ssh and database connection
 */
public class Credentials{
    String sshId;
    String sshPassword;
    String sandboxId;
    String sandboxPassword;
    int dbPort;

    public Credentials(String sshID, String sshPassword, String dbID, String dbPassword, int dbPort){
        this.sshId = sshID;
        this.sshPassword = sshPassword;
        this.sandboxId = dbID;
        this.sandboxPassword = dbPassword;
        this.dbPort = dbPort;

    }

    public String getSshId() {
        return sshId;
    }

    public String getSshPassword() {
        return sshPassword;
    }

    public String getSandboxId() {
        return sandboxId;
    }

    public String getSandboxPassword() {
        return sandboxPassword;
    }

    public int getDbPort() {
        return dbPort;
    }
}