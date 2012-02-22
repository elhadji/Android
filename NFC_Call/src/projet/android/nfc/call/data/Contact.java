package projet.android.nfc.call.data;

public class Contact {

    private int id;
    private String first_name;
    private String last_name;
    private String tel	;

    public Contact() {}
    
    public Contact(String first_name, String last_name, String tel ) {		
        this.first_name = first_name;
        this.last_name = last_name;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {		
        this.id = id;		
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String name) {		
        this.last_name = name;		
    }
    
    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String name) {		
        this.first_name = name;		
    }

    public String getTel() {		
        return tel;		
    }

    public void setTel(String tel) {		
        this.tel = tel;		
    }
    public String toString() {
		return "ID: "+id +" nom: "+first_name;
	}

}
