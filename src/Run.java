import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;


public class Run {
	
	private ArrayList<PhoneBook> contactList = new ArrayList<PhoneBook>();
	private OrderedDictionary dictionary = new OrderedDictionary();
	private HashMap<String, LinkedList<Integer>> hasMap = new HashMap<String, LinkedList<Integer>>();
	
	private Scanner scanner = new Scanner(System.in);
	private String selectInput="";
	private Scanner scanner2;
	private Scanner scanner3;
	private Scanner scanner4;
	
	
	public Run() {
		
		//Function.showMainMenu();
	
		while(!selectInput.toLowerCase().equals("x")){
			
			//System.out.println();
			System.out.print("Phonebook - Input your command (h for help) : ");
			selectInput = scanner.next();
			
			if(selectInput.toLowerCase().equals("i")){
				insert();				
			} else if(selectInput.toLowerCase().equals("d")){
				delete();
			} else if(selectInput.toLowerCase().equals("m")){
				modify();
			} else if(selectInput.toLowerCase().equals("s")){
				search();
			} else if(selectInput.toLowerCase().equals("h")){
				Function.showMainMenu();
			} else {
				
			}
			
		}
		
	}
	
	private void insert(){
		String input ="";
		String select = "";
		String reNum = "";
		String newNum = "";
		String phoneNumber = "";
		PhoneBook phoneBook = new PhoneBook();
		ArrayList<String> numberList = new ArrayList<String>();
		

		System.out.print("Enter a phone number to be inserted: ");
		input = scanner.next();
		if(Function.isValidNumber(input,10)==true){
			
			Function.showContactList(contactList);
			if(contactList.size()==0){
				addNewContact(input);
			}else {
				System.out.print("Enter the person id to append the phone number or 'n' for new person :");	
				select = scanner.next();
				
				if(select.toLowerCase().equals("n")){
					addNewContact(input);
				} else {
					phoneBook = contactList.get(Integer.parseInt(select)-1);
					numberList = phoneBook.getPhoneNumberList();
					System.out.print("Enter replaced number or 'n' for new phone number :");	
					reNum = scanner.next();	
					if(reNum.toLowerCase().equals("n")){
						try {
							if(contactList.get(Integer.parseInt(select)-1)!=null){
								appendContact(Integer.parseInt(select)-1,input);
								Function.showNewContactList(contactList);
								System.out.println();
							} else {
								System.out.println("Invalid Id");
							}
							
						} catch (Exception e) {
							System.out.println("Invalid Id");
						}
					} else {
						System.out.print("Do You want to replace "+phoneBook.getName()+"'s contact "+numberList.get(Integer.parseInt(reNum)-1)+" with "+input+" ('y' for yes, any key to cancle)?  :");	
						newNum = scanner.next();
						
						if(newNum.toLowerCase().equals("y")){
							try {
								if(Function.isValidNumber(input,10)==true){
									phoneNumber = numberList.get(0);
									numberList.remove(0);
									phoneBook.setPhoneNumberList(numberList);
									contactList.set(Integer.parseInt(select)-1, phoneBook);
									reHash(phoneNumber,Integer.parseInt(select)-1);
				//					contactList.remove(arrayIndex);
									
									ArrayList<String> newNumberList = new ArrayList<String>();
									newNumberList.addAll(phoneBook.getPhoneNumberList());
									newNumberList.add(input);
									phoneBook.setPhoneNumberList(newNumberList);
									contactList.set(Integer.parseInt(select)-1,phoneBook);
									mapping(Integer.parseInt(select)-1, input);
								} else {
									System.out.println("Invalid Input");
									return;
								}
									
							} catch (Exception e){
								System.out.println("Invalid Id");
							}
							Function.showNewContactList(contactList);
						} else {
							
						}
					}
				}
				
			}

		} else {
			
		}
	}	
		
	
	
	private void delete(){
		String input = "";
		
		Function.showContactList(contactList);
		System.out.print("Enter the person id to be deleted or any key to cancel :");
		input = scanner.next();
		
		try {
			deleteNumber(Integer.parseInt(input)-1);
			Function.showNewContactList(contactList);
			System.out.println();
			
		} catch (Exception e) {
			System.out.println("Invalid Input");
		}
		
	}
	
	private void modify(){
		String input = "";
		
		Function.showContactList(contactList);
		System.out.print("Enter the person id to be modified or any key to cancel:");
		input = scanner.next();
		
		try {
			modifyNumber(Integer.parseInt(input)-1);
			Function.showNewContactList(contactList);
			System.out.println();
			
		} catch (Exception e) {
			System.out.println("Invalid Input");
		}
		
	}
	
	private void search(){
		ArrayList<PhoneBook> tempList = new ArrayList<PhoneBook>();
		LinkedList<Integer> list = new LinkedList<Integer>();
		
		String key ="";
		System.out.print("Enter 3 digits to search: ");
		key = scanner.next();
		
		if(Function.isValidNumber(key,3)==false)
			return;
		
		
		list = searchByKey(key);
		
		
		tempList.addAll(this.contactList);

		if(list!=null)
			Function.printSearchResult(key, list,tempList);
		else
			System.out.println("No result"); 
		
		System.out.println();
	}
	

	
	private LinkedList<Integer> searchByKey(String key){
		LinkedList<Integer> list = new LinkedList<Integer>();
		list = hasMap.get(key);
		
		
		return list;
	}
	
	
	
	private void addNewContact(String phoneNumber){
		
		scanner2 = new Scanner(System.in);
		String name = "";
		System.out.print("Enter the name (8 characters maximum): ");
		name = scanner2.nextLine();
		
		if(name.length()>8){
			name = name.substring(0, 8);
			
		} else {
			String tempName = name;
			for(int i=8;i>tempName.length();i--){
				name = name + " ";	
			}
		}
		
		PhoneBook contact = new PhoneBook();
		contact.setName(name);
		
		ArrayList<String> numberList = new ArrayList<String>();
		numberList.add(phoneNumber);
		
		contact.setPhoneNumberList(numberList);
		contactList.add(contact);
		
		mapping(contactList.size()-1, phoneNumber);
	}
	
	private void appendContact(int arrayIndex,String phoneNumber){
		
		PhoneBook phoneBook = new PhoneBook();
		phoneBook = contactList.get(arrayIndex);
		String newNum2 = "";
		if(phoneBook.getPhoneNumberList().size()<5){
			ArrayList<String> numberList = new ArrayList<String>();
			numberList.addAll(phoneBook.getPhoneNumberList());
			numberList.add(phoneNumber);
			phoneBook.setPhoneNumberList(numberList);
			contactList.set(arrayIndex,phoneBook);
			mapping(arrayIndex, phoneNumber);
		}
		else 
			System.out.println(phoneBook.getName() + " has 5 phone numbers already, enter replaced number or any key for cancel:");
		newNum2 = scanner.next();
		
			if(newNum2.toLowerCase().equals("y")){
				try {
					if(Function.isValidNumber(phoneNumber,10)==true){
						ArrayList<String> numberList = new ArrayList<String>();
						phoneNumber = numberList.get(0);
						numberList.remove(0);
						phoneBook.setPhoneNumberList(numberList);
						contactList.set(arrayIndex, phoneBook);
						reHash(phoneNumber,arrayIndex);
	//					contactList.remove(arrayIndex);
						
						ArrayList<String> newNumberList = new ArrayList<String>();
						newNumberList.addAll(phoneBook.getPhoneNumberList());
						newNumberList.add(phoneNumber);
						phoneBook.setPhoneNumberList(newNumberList);
						contactList.set(arrayIndex,phoneBook);
						mapping(arrayIndex, phoneNumber);
					} else {
						System.out.println("Invalid Input");
						return;
					}
						
				} catch (Exception e){
					System.out.println("Invalid Id");
				}
				Function.showNewContactList(contactList);
			} else {
				
			}
		}
	
	
	private void deleteNumber(int arrayIndex){
		String phoneNumber = "";
		PhoneBook phoneBook = new PhoneBook();
		ArrayList<String> numberList = new ArrayList<String>();
		scanner3 = new Scanner(System.in);
		String input = "";
		
		phoneBook = contactList.get(arrayIndex);
		numberList = phoneBook.getPhoneNumberList();
		if(numberList.size()>1){
			System.out.print("Enter the phone number id to be deleted: ");
		}else {
			System.out.print("Do you want to delete "+phoneBook.getName()+"'s contact as well ('y' for yes, any key to cancel)?: ");
		}
		input = scanner3.nextLine();
		try {
			if(numberList.size()>1){
				phoneNumber = numberList.get(Integer.parseInt(input)-1);
				numberList.remove(Integer.parseInt(input)-1);
				phoneBook.setPhoneNumberList(numberList);
				contactList.set(arrayIndex, phoneBook);
				reHash(phoneNumber,arrayIndex);
			} else {
				if(input.toLowerCase().equals("y")){
					phoneNumber = numberList.get(0);
					numberList.remove(0);
					phoneBook.setPhoneNumberList(numberList);
					contactList.set(arrayIndex, phoneBook);
					reHash(phoneNumber,arrayIndex);
					contactList.remove(arrayIndex);
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Invalid Input");
			return;
		}

	}
	
	private void modifyNumber(int arrayIndex){
		String phoneNumber = "";
		PhoneBook phoneBook = new PhoneBook();
		ArrayList<String> numberList = new ArrayList<String>();
		scanner4 = new Scanner(System.in);
		String input = "";
		String newNumber = "";
		
		phoneBook = contactList.get(arrayIndex);
		numberList = phoneBook.getPhoneNumberList();
		if(numberList.size()>1){
			System.out.print("Enter the phone number id to be modified: ");
		}else {
			System.out.print("Do you want to modified "+phoneBook.getName()+"'s contact as well ('y' for yes, any key to cancel)?: ");
		}
		input = scanner4.nextLine();
		try {
			if(numberList.size()>1){
				System.out.print("Enter the new phone number: ");
				newNumber = scanner4.nextLine();
				if(Function.isValidNumber(newNumber,10)==true){
					phoneNumber = numberList.get(Integer.parseInt(input)-1);
					numberList.remove(Integer.parseInt(input)-1);
					phoneBook.setPhoneNumberList(numberList);
					contactList.set(arrayIndex, phoneBook);	
					reHash(phoneNumber,arrayIndex);
					
					ArrayList<String> newNumberList = new ArrayList<String>();
					newNumberList.addAll(phoneBook.getPhoneNumberList());
					newNumberList.add(Integer.parseInt(input)-1,newNumber);
					phoneBook.setPhoneNumberList(newNumberList);
					contactList.set(arrayIndex,phoneBook);
					mapping(arrayIndex, newNumber);
					
				
				} else {
					System.out.println("Invalid Input");
					return;
				}
				
			} else {
				if(input.toLowerCase().equals("y")){
					System.out.print("Enter the new phone number: ");
					newNumber = scanner4.nextLine();
					if(Function.isValidNumber(newNumber,10)==true){
						phoneNumber = numberList.get(0);
						numberList.remove(0);
						phoneBook.setPhoneNumberList(numberList);
						contactList.set(arrayIndex, phoneBook);
						reHash(phoneNumber,arrayIndex);
	//					contactList.remove(arrayIndex);
						
						ArrayList<String> newNumberList = new ArrayList<String>();
						newNumberList.addAll(phoneBook.getPhoneNumberList());
						newNumberList.add(newNumber);
						phoneBook.setPhoneNumberList(newNumberList);
						contactList.set(arrayIndex,phoneBook);
						mapping(arrayIndex, newNumber);
					} else {
						System.out.println("Invalid Input");
						return;
					}
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Invalid Input");
			return;
		}

	}
		
	private void mapping(int arrayIndex,String phoneNumber){
		
		String key = "";
		Queue<String> keyQueue = new LinkedList<String>();
		
		for(int i=0;i<8;i++){
			keyQueue.add(phoneNumber.substring(i,i+3));
			
		}
		
		while(keyQueue.isEmpty()==false){

			if(dictionary.key==null){
				key = keyQueue.poll();
				dictionary.key = key;
				
				dictionary.listIndex = new LinkedList<Integer>();
				dictionary.listIndex.add(arrayIndex);
				
				newHashMap(key,arrayIndex);
				upToRoot();
			
			} else {
				if(dictionary.key.compareTo(keyQueue.peek())>0){
					if(dictionary.leftNode==null){
						dictionary.leftNode = new OrderedDictionary();
						OrderedDictionary leftNode = dictionary.leftNode;
						leftNode.parent = dictionary;
						dictionary = leftNode;
						
					} else {
						dictionary = dictionary.leftNode;
					}
					
				} else if(dictionary.key.compareTo(keyQueue.peek())<0){
					if(dictionary.rightNode==null){
						dictionary.rightNode = new OrderedDictionary();
						OrderedDictionary rightNode = dictionary.rightNode;
						rightNode.parent = dictionary;
						dictionary = rightNode;
						
					} else {
						dictionary = dictionary.rightNode;
					}
					
				} else {
					key = keyQueue.poll();
					boolean isExistIndex = false;
					LinkedList<Integer> list = new LinkedList<Integer>();
					list.addAll(dictionary.listIndex);
					
					for(Integer index:list){
						if(index==arrayIndex){
							isExistIndex = true;
							break;		
						}
						
					}
					if(!isExistIndex){
						list.add(arrayIndex);
						updateHashMap(key, arrayIndex);
					}
					
					upToRoot();		
				}
			}
		}

		
	}
	

	
	private OrderedDictionary addNode(OrderedDictionary node,OrderedDictionary tree){

		boolean isAdd = false;
		
		if(node!=null){
			if(node.key==null){
				return tree;
			}
			while(isAdd==false){
				
				if(tree.key==null){
					node.parent = null;
					tree = node;
				
					upToRoot();
					isAdd = true;
				} else {
					if(tree.key.compareTo(node.key)>0){
						if(tree.leftNode==null){
							tree.leftNode = new OrderedDictionary();
							node.parent = tree;
							tree.leftNode = node;
							isAdd = true;
							upToRoot();
						} else {
							tree = tree.leftNode;
						}
						
					} else if(tree.key.compareTo(node.key)<0){
						if(tree.rightNode==null){
							tree.rightNode = new OrderedDictionary();
							node.parent = tree;
							tree.rightNode = node;
							isAdd = true;
							upToRoot();
						} else {
							tree = tree.rightNode;
						}
						
					} 

				}
			}
			
		}
		
		return tree;
		
		
	}
	
	private void newHashMap(String key,int arrayIndex){
		
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.add(arrayIndex);
		hasMap.put(key, list);
			
	}
	
	private void updateHashMap(String key,int arrayIndex){
		boolean isExistIndex = false;
		LinkedList<Integer> list = new LinkedList<Integer>();
		list = hasMap.get(key);
		
		for(Integer index:list){
			if(index==arrayIndex){
				isExistIndex = true;
				break;		
			}
			
		}
		if(!isExistIndex){
			list.add(arrayIndex);
			hasMap.put(key, list);
		}
		
	}
	
	
	private void reHash(String phoneNumber,int arrayIndex){
		ArrayList<String> numberList = new ArrayList<String>();
		ArrayList<String> keyList = new ArrayList<String>();
		ArrayList<String> delList = new ArrayList<String>();
		
		PhoneBook phoneBook = new PhoneBook();
		phoneBook = contactList.get(arrayIndex);
		numberList = phoneBook.getPhoneNumberList();
		
		for(String str:numberList){
			for(int i=0;i<8;i++){
				String key = "";
				key = str.substring(i,i+3);
				if(isExistKey(keyList, key)==false){
					keyList.add(str.substring(i,i+3));
				}
				
			}
			
		}
		for(int i=0;i<8;i++){
			String key = "";
			key = phoneNumber.substring(i, i+3);
			if(isExistKey(keyList, key)==false){
				delList.add(key);
			}
		}
		
		for(String key:delList){
			deleteKeyHash(key);
			deleteDataDictKey(key);
		}
	}

	private void deleteKeyHash(String key){
		
			hasMap.put(key, null);	
	}
	
	private boolean isExistKey(ArrayList<String> keyList,String key){
		boolean isExist = false;
		
		for(String str:keyList){
			if(str.equals(key)){
				isExist = true;
				break;
			}
			
		}
		return isExist;
		
	}
	
	private void upToRoot(){
		
		boolean isRoot = false;
		
		while(!isRoot){
			if(dictionary.parent!=null){
				dictionary = dictionary.parent;
			}else {
				isRoot = true;
			}
		}
		
		System.out.print("");
	}
	
	private void deleteDataDictKey(String key){
		boolean isDelete = false;
		String parent = "null";
		while(!isDelete){
			if(dictionary.key.compareTo(key)>0){
				dictionary = dictionary.leftNode;
				parent = "left";
				
			} else if(dictionary.key.compareTo(key)<0){
				dictionary = dictionary.rightNode;
				parent = "right";
				
			} else {
				OrderedDictionary left = new OrderedDictionary();
				OrderedDictionary right = new OrderedDictionary();
				
				left = dictionary.leftNode;
				right = dictionary.rightNode;
				
				if(left==null && right==null){
					if(parent.equals("left")){
						dictionary = dictionary.parent;
						dictionary.leftNode = null;
					} else if(parent.equals("right")) {
						dictionary = dictionary.parent;
						dictionary.rightNode = null;
					} else {
						dictionary.key = null;
					}
					
				} else {
					OrderedDictionary leftNode = new OrderedDictionary();
					OrderedDictionary rightNode = new OrderedDictionary();
					leftNode = dictionary.leftNode;
					rightNode = dictionary.rightNode;
					
					if(parent.equals("left")){
						dictionary = dictionary.parent;
						dictionary.leftNode = null;
						dictionary = addNode(leftNode,dictionary);
						dictionary = addNode(rightNode,dictionary);
					} else if(parent.equals("right")) {
						dictionary = dictionary.parent;
						dictionary.rightNode = null;
						dictionary = addNode(leftNode,dictionary);
						dictionary = addNode(rightNode,dictionary);
					} else {
						if(left!=null && right==null){
							dictionary = dictionary.leftNode;
						} else if(left==null && right!=null){
							dictionary = dictionary.rightNode;
						}
						else {
							dictionary = leftNode;
							dictionary.parent = null;
							dictionary = addNode(rightNode,dictionary);
						}
					}
				}

				
				isDelete = true;
				upToRoot();
				
			}
			
		}
		
		
	}
	
	public void endProgram(){
		System.out.println("End....");
		
	}
	
	private static class KeySorting implements Comparator<Integer>{

		@Override
		public int compare(Integer o1, Integer o2) {

			return o1-o2;
		}

	};
	
	
}
