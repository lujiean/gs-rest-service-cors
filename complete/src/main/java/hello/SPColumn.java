package hello;

public class SPColumn{

    private int seq;
    private String name;
    private int def;
    //1 -- char
    //2 -- int
    private boolean optional;

    public SPColumn(int seq, String name, int def, boolean optional) {
        this.seq = seq;
        this.name = name;
        this.def = def;
        this.optional = optional;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    } 

    public int getSeq() {
        return seq;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getDef() {
        return def;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public boolean getOptional() {
        return optional;
    }

}