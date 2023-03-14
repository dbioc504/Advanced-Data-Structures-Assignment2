class Customer {
  private int ID;
  private int age;

  public Customer(int ID, int age) {
    this.ID = ID;
    this.age = age;
  }

  public int getAge() {return age;}

  public String toString() {
    return ID + ": " + age;
  }
}