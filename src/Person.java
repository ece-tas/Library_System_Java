public class Person {
    private String _person;
    private int id;

    /**

     Constructs a new Person object with the given person type and ID.
     @param _person the type of the person ("S" for Student, "A" for Academic)
     @param countPeople the ID of the person
     */
    public Person(String _person, int countPeople) {
        this._person = _person;
        this.id = countPeople;
    }

    /**

     Returns the type of the person.
     @return the type of the person ("Student" or "Academic") or "ERROR" if the person type is unknown
     */
    private String getType() {
        if (getPerson().equals("S")) {
            return "Student";
        } else if (getPerson().equals("A")) {
            return "Academic";
        } else {
            return "ERROR";
        }
    }

    /**

     Returns a string representation of the person.
     @return a string representation of the person in the format "Created new member: [type] [id]"
     or "ERROR" if the person type is unknown
     */
    @Override
    public String toString() {
        if (getType().equals("ERROR")) {
            return "ERROR";
        } else {
            return String.format("Created new member: %s [id: %d]", getType(), id);

        }
    }

    /**

     Returns the person type.
     @return the person type
     */
    public String getPerson() {
        return _person;
    }
}
