import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class Function {
	
	
	
	private static Scanner scanner;


	public static void showMainMenu(){
		System.out.println("");
		System.out.println("i - insert a phone number");
		System.out.println("d - delete a phone number");
		System.out.println("m - modify a phone number");
		System.out.println("s - search");
		System.out.println("x - exit program");
		System.out.println("");
	}
	
	public static boolean isValidNumber(String number,int length){
		boolean valid = false;
		if(number.length()>length){
			System.out.println("Error - the number is too long.");
			valid = false;
		
		}else if(number.length()<length){
			System.out.println("Error - the number is too short.");
			valid = false;
		}else {
			for(int i=0;i<length;i++){
				if(number.charAt(i)<'0' || number.charAt(i)>'9'){
					valid = false;
					System.out.println("Error - invalid number");
					break;
				}
				valid = true;
				
			}
		}
		
		return valid;
		
	}
	
	public static void showContactList(ArrayList<PhoneBook> contactList){
		PhoneBook contact;
		
		if(!contactList.isEmpty()){
			System.out.println("The list is: ");
			
			for(int index=0;index<contactList.size();index++){
				contact = new PhoneBook();
				contact = contactList.get(index);
				System.out.print("[" + (index+1) + "] " + contact.getName()+ " ");
				System.out.println("1 - " + contact.getPhoneNumberList().get(0));
				if(contact.getPhoneNumberList().size()>1){
					for(int i=1;i<contact.getPhoneNumberList().size();i++){
						System.out.print("             ");
						System.out.println((i+1) + " - " + contact.getPhoneNumberList().get(i));
					}
				}
				
			}

			
		} else {
			System.out.println("The list is: Empty");			
		}
		System.out.println();
	}
	
	public static void showNewContactList(ArrayList<PhoneBook> contactList){
		PhoneBook contact;
		
		if(!contactList.isEmpty()){
			System.out.println("The new list is: ");
			
			for(int index=0;index<contactList.size();index++){
				contact = new PhoneBook();
				contact = contactList.get(index);
				System.out.print("[" + (index+1) + "] " + contact.getName()+ " ");
				System.out.println("1 - " + contact.getPhoneNumberList().get(0));
				if(contact.getPhoneNumberList().size()>1){
					for(int i=1;i<contact.getPhoneNumberList().size();i++){
						System.out.print("             ");
						System.out.println((i+1) + " - " + contact.getPhoneNumberList().get(i));
					}
				}
				
			}

			
		} else {
			System.out.println("The list is: Empty");			
		}
		System.out.println();
	}
	
	
	public static PhoneBook setNewContact(int id,String number){
		scanner = new Scanner(System.in);
		String name = "";
		System.out.println("Enter the name (8 characters maximum): ");
		name = scanner.next();
		
		if(name.length()>8){
			name = name.substring(0, 8);
			
		} else {
			String tempName = name;
			for(int i=8;i<tempName.length();i--){
				name = name + " ";	
			}
		}
		
		PhoneBook contact = new PhoneBook();
		contact.setName(name);
		
		ArrayList<String> numberList = new ArrayList<String>();
		numberList.add(number);
		
		contact.setPhoneNumberList(numberList);
		
		return contact;
		
	}
	
	public static PhoneBook appendNumber(PhoneBook contact,String number){
		
		PhoneBook updContact = new PhoneBook();
		updContact = contact;
		
		ArrayList<String> numberList = new ArrayList<String>();
		numberList = contact.getPhoneNumberList();
		numberList.add(number);
		
		updContact.setPhoneNumberList(numberList);
		
		return updContact;
		
	}
	
	public static void printSearchRangeResult(ArrayList<String> keyList,LinkedList<Integer> indexList,ArrayList<PhoneBook> contactList){
		ArrayList<PhoneBook> phoneBookList = new ArrayList<PhoneBook>();
		LinkedList<Integer> iList = new LinkedList<Integer>();
		iList.addAll(indexList);
		phoneBookList.addAll(contactList);
		
		for(Integer index:indexList){
			String name = phoneBookList.get(index).getName();
			ArrayList<String> numberList = new ArrayList<String>();
			numberList.addAll(phoneBookList.get(index).getPhoneNumberList());
			
			ArrayList<String> selectList = new ArrayList<String>();
			boolean isDup = false;
			for(String phoneNumber:numberList){
				for(String key:keyList){
					for(int i=0;i<8;i++){
						if(phoneNumber.substring(i, i+3).equals(key)){
							isDup = false;
							for(String sel:selectList){
								if(phoneNumber.equals(sel)){
									isDup = true;
									break;
									
								}
							}
							if(!isDup){
								selectList.add(phoneNumber);
								break;
							}
							
						}
						
					}	
				}
			}
			
			
			
			System.out.print("[" + (index+1) + "] " + name+ " ");
			System.out.println("1 - " + selectList.get(0));
			if(selectList.size()>1){
				for(int i=1;i<selectList.size();i++){
					System.out.print("             ");
					System.out.println((i+1) + " - " + selectList.get(i));
				}
			}
			
		}
			
		
		
		
		
	}
	
	
	public static void printSearchResult(String key,LinkedList<Integer> indexList,ArrayList<PhoneBook> contactList){

		
		ArrayList<PhoneBook> phoneBookList = new ArrayList<PhoneBook>();
		LinkedList<Integer> iList = new LinkedList<Integer>();
		iList.addAll(indexList);
		phoneBookList.addAll(contactList);
		
		for(Integer index:indexList){
			String name = phoneBookList.get(index).getName();
			ArrayList<String> numberList = new ArrayList<String>();
			numberList.addAll(phoneBookList.get(index).getPhoneNumberList());
			
			ArrayList<String> selectList = new ArrayList<String>();
			for(String phoneNumber:numberList){
				for(int i=0;i<8;i++){
					if(phoneNumber.substring(i, i+3).equals(key)){
						selectList.add(phoneNumber);
						break;
					}
					
				}	
			}
			
			
			
			System.out.print("[" + (index+1) + "] " + name+ " ");
			System.out.println("1 - " + selectList.get(0));
			if(selectList.size()>1){
				for(int i=1;i<selectList.size();i++){
					System.out.print("             ");
					System.out.println((i+1) + " - " + selectList.get(i));
				}
			}
			
		}
		
	}
	
	
	

}
