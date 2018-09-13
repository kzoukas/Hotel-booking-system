package vaseis;



import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;
















import vaseis.ZipfGenerator;
import vaseis.connectToDB;


public class MyMain {
	public static Date trexousa_hmeromhnia;
	public static String cities[]= new String[10];
	public static String surnames[] = new String[200];
	public static String names[] = new String[50];
	
	static Vector<Integer> person_id = new Vector<Integer>();
	static Vector<Integer> hotel_ID = new Vector<Integer>();
	static Vector<Integer> roomid_free_ofhotel = new Vector<Integer>();
	static Vector<Integer> clients_id = new Vector <Integer>();
	
	static Vector<Integer> manager_ID = new Vector<Integer>();
	static Vector<String> typename = new Vector <String>();
	static Vector<String> employees_roles = new Vector <String>();
	public static connectToDB conne;
	public static ZipfGenerator zipf=new ZipfGenerator(new Random(System.currentTimeMillis()));
	
	
	public static void login(){
		try {
			conne=new connectToDB("MyDB");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	//////////  2

	public static void instertHotelTable() throws SQLException{
		PreparedStatement prst = null;   // we are going to insert hotels in table hotel with PreparedStatement
		Random RandomNum = new Random();
		
		for(int i=1;i<=100;i++){    //we insert 100 hotels
			int temp=RandomNum.nextInt(5)+1;   //RandomNum.nextInt gets values from 0 to 4 we need 1 to 5
			prst=MyMain.conne.conn.prepareStatement("insert into hotel values(?,?,?,?,?,?,?,?)");
			prst.setInt(1, i);
			prst.setString(2, "hotelname_"+i);
			
			prst.setString(3,temp+"star");    //we insert stars with randomNum
			prst.setString(4, "hoteladdress_"+i);
			int zipf_city = zipf.zipf(10);      //we insert cities with zipf, zipf_city gets values from 0 to 9
			prst.setString(5, cities[zipf_city]);  
			if (zipf_city== 0 || zipf_city== 1 || zipf_city== 2 || zipf_city== 3 ){
				prst.setString(6, "Brasil");
			}
			if (zipf_city== 4 || zipf_city== 5 || zipf_city== 6 || zipf_city== 7){
				prst.setString(6, "Egypt");
			}
			if (zipf_city== 8 || zipf_city== 9){
				prst.setString(6, "Thailand");
			}
			prst.setString(7, "hotelphone_"+i);
			prst.setString(8, "hotelfax_"+i);
			prst.executeUpdate();
		}
	}
	public static void insterRoomtypeTable() throws SQLException{
		PreparedStatement prst = null;
		for(int i=1;i<=10;i++){   //we insert 10 roomtypes 
			prst=MyMain.conne.conn.prepareStatement("insert into roomtype values(?)");
			prst.setString(1, "typename_"+i);
			prst.executeUpdate();
		}
	}
	public static void insertRoomrateTable() throws SQLException{
		Random RandomNum = new Random();
		
		
		PreparedStatement prst = null;
		PreparedStatement prst1 = null;
		PreparedStatement prst2 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		prst=MyMain.conne.conn.prepareStatement("SELECT \"idHotel\" FROM hotel");
		rs = prst.executeQuery();
		while(rs.next()){
			hotel_ID.add(rs.getInt(1));
		}
		prst1=MyMain.conne.conn.prepareStatement("SELECT \"typename\" FROM roomtype");
		rs1 = prst1.executeQuery();
		while(rs1.next()){
			typename.add(rs1.getString(1));
			System.out.println(rs1.getString(1));
		}
		
		for(int j=1;j<=100;j++){   //gia ka8e 3enodoxeio 10 domatia
			for(int i=1;i<=10;i++){
				prst2=MyMain.conne.conn.prepareStatement("insert into roomrate values(?,?,?,?)");
				prst2.setInt(1,hotel_ID.get(j-1));
				prst2.setString(2,typename.get(i-1));
				prst2.setInt(3, RandomNum.nextInt(40)+100);  
				prst2.setFloat(4,(float) (RandomNum.nextInt(5)*0.1));
				prst2.executeUpdate();
				
			}
		}
	}
	public static void insertRoomTable() throws SQLException{
		Random RandomNum = new Random();
		int orofoi_number,domatia_number=0;
		PreparedStatement prst = null;
		
		orofoi_number = RandomNum.nextInt(5) + 4;
		domatia_number = RandomNum.nextInt(20) + 10;
		for(int j=1;j<=100;j++){ 						//gia ka8e 3enodoxeio
			orofoi_number = RandomNum.nextInt(5) + 4;   //epilegete tuxaia o ar ton orofon kai ton domation ana orofo
			domatia_number = RandomNum.nextInt(20) + 10;
			for(int i=1;i<=orofoi_number;i++){			//gia ka8e orofo
				for(int k=1;k<=domatia_number;k++){		//gia ka8e domatio
					prst=MyMain.conne.conn.prepareStatement("insert into room values(?,?,?,?,?,?)");
					prst.setInt(1,k+((i-1)*domatia_number + (j*1000))); //idroom
					prst.setInt(2, i*100 + k + (j*1000)); 				 //number of room
					int temp3 = RandomNum.nextInt(10);
					if (temp3>3){	//vacant
						prst.setInt(3,0);
					}else prst.setInt(3,1);
									
					float temp = (float) ((RandomNum.nextInt(11) + 10) + (RandomNum.nextInt(10)*0.1));
					prst.setFloat(4, temp);
					prst.setInt(5,hotel_ID.get(j-1));
					int temp2 = RandomNum.nextInt(10);
					prst.setString(6,typename.get(temp2));
					prst.executeUpdate();
					
				}
				
			}
		}
	}
	
	public static void insertTravelagentTable() throws SQLException{
		PreparedStatement prst = null;
		
		for(int i=1;i<=50;i++){
			prst=MyMain.conne.conn.prepareStatement("insert into travelagent values(?,?,?)");
			prst.setInt(1, i);
			prst.setString(2, "travel_agent_name_"+i);
			prst.setString(3, "travel_agent_email_"+i);
			prst.executeUpdate();
		}
	}
		
	public static void insertEmployee_Person() throws SQLException{
		Random RandomNum = new Random();
		int numberOfEmployees;
		int numberOfManagers=0;
		PreparedStatement prst = null;
		PreparedStatement prst2 = null;
		for(int j=1;j<=100;j++){ 						//gia ka8e 3enodoxeio
			numberOfEmployees = RandomNum.nextInt(10) + 10;
			numberOfManagers = RandomNum.nextInt(3) + 2;
			manager_ID.clear();
			
			for(int i=1;i<=numberOfManagers;i++){ //we fill the managers
				prst = MyMain.conne.conn.prepareStatement("insert into person values(?,?,?,?,?,?,?,?)");
				prst.setInt(1, i+j*100);
				int selectName = zipf.zipf(50);
				prst.setString(2, names[selectName]);
				selectName = zipf.zipf(200);
				prst.setString(3, surnames[selectName]);
				prst.setInt(4, RandomNum.nextInt(2));
				prst.setDate(5,null );
				int zipf_city = zipf.zipf(10);	
				prst.setString(6, cities[zipf_city]);
				if (zipf_city== 0 || zipf_city== 1 || zipf_city== 2 || zipf_city== 3 ){
					prst.setString(7, "Brasil");
				}
				if (zipf_city== 4 || zipf_city== 5 || zipf_city== 6 || zipf_city== 7){
					prst.setString(7, "Egypt");
				}
				if (zipf_city== 8 || zipf_city== 9){
					prst.setString(7, "Thailand");
				}
				
				prst.setString(8, "Person_Address_"+i);
				prst.executeUpdate();
				prst2 = MyMain.conne.conn.prepareStatement("insert into employee values(?,?,?,?)");
				manager_ID.add(i);
				prst2.setInt(1,i+j*100);
				prst2.setString(2,"manager" );
				prst2.setNull(3,0);
				prst2.setInt(4,j );
				prst2.executeUpdate();
			}
			prst=null;
			prst2=null;
			
			for(int i=1 + numberOfManagers;i<=numberOfEmployees;i++){
				prst = MyMain.conne.conn.prepareStatement("insert into person values(?,?,?,?,?,?,?,?)");
				prst.setInt(1, i+j*100);
				int selectName = zipf.zipf(50);
				prst.setString(2, names[selectName]);
				selectName = zipf.zipf(200);
				prst.setString(3, surnames[selectName]);
				prst.setInt(4, RandomNum.nextInt(2));
				prst.setDate(5,null );
				int zipf_city = zipf.zipf(10);	
				prst.setString(6, cities[zipf_city]);
				if (zipf_city== 0 || zipf_city== 1 || zipf_city== 2 || zipf_city== 3 ){
					prst.setString(7, "Brasil");
				}
				if (zipf_city== 4 || zipf_city== 5 || zipf_city== 6 || zipf_city== 7){
					prst.setString(7, "Egypt");
				}
				if (zipf_city== 8 || zipf_city== 9){
					prst.setString(7, "Thailand");
				}
				
				prst.setString(8, "Person_Address_"+i);
				prst.executeUpdate();
				prst2 = MyMain.conne.conn.prepareStatement("insert into employee values(?,?,?,?)");
				prst2.setInt(1,i+j*100);
				prst2.setString(2,"receptionist" );
				prst2.setInt(3,manager_ID.get(RandomNum.nextInt(numberOfManagers))+j*100);
				prst2.setInt(4,j );
				prst2.executeUpdate();
			}
		}
	}
	
	public static void insertClient_Person() throws SQLException{ //code in idPerson for Client 
		PreparedStatement prst = null;
		PreparedStatement prst2 = null;
		Random RandomNum = new Random();
		int documentClient=100000000;
		
		
		for(int i=1;i<=20;i++){
			prst = MyMain.conne.conn.prepareStatement("insert into person values(?,?,?,?,?,?,?,?)");
			prst.setInt(1, 99*1000+i);
			int selectName = zipf.zipf(50);
			prst.setString(2, names[selectName]);
			selectName = zipf.zipf(200);
			prst.setString(3, surnames[selectName]);
			prst.setInt(4, RandomNum.nextInt(2));
			prst.setDate(5,null );
			int zipf_city = zipf.zipf(10);	
			prst.setString(6, cities[zipf_city]);
			if (zipf_city== 0 || zipf_city== 1 || zipf_city== 2 || zipf_city== 3 ){
				prst.setString(7, "Brasil");
			}
			if (zipf_city== 4 || zipf_city== 5 || zipf_city== 6 || zipf_city== 7){
				prst.setString(7, "Egypt");
			}
			if (zipf_city== 8 || zipf_city== 9){
				prst.setString(7, "Thailand");
			}
			prst.setString(8, "Person_Address_"+i);
			prst.executeUpdate();
			
			prst2 = MyMain.conne.conn.prepareStatement("insert into client values (?,?)");
			prst2.setInt(1,99*1000+i);
			prst2.setString(2,"ID_"+documentClient+i);
			prst2.executeUpdate();
		}
		
	}
	
	public static void insertCreditCardTable() throws SQLException{
		Random RandomNum = new Random();
		int numberOfcreditCards;
		PreparedStatement prst = null;
		int credicardNum = 1000;
		
		for(int i=1;i<=20;i++){
			credicardNum = 1000;
			if (RandomNum.nextInt(10) <=7){   //o client exei creditCard
				numberOfcreditCards = zipf.zipf(5) + 1;  
				for(int j=1;j<=numberOfcreditCards;j++){
					credicardNum = credicardNum + 1;
					prst = MyMain.conne.conn.prepareStatement("insert into creditcard values (?,?,?,?,?)");
					prst.setString(1,"cardtype_"+(RandomNum.nextInt(5)+1));
					prst.setString(2, "" + credicardNum);
					prst.setNull(3, 0);
					int selectName = zipf.zipf(50);
					int selectSurName = zipf.zipf(200);
					prst.setString(4, names[selectName] +" "+ surnames[selectSurName]);
					prst.setInt(5, 99*1000+i);
					prst.executeUpdate();
				}
			}
			
		}
		
		
	}
	/////////////////////////////////////////////////////////////////////////////////
	
	
	
	public static void insertFacility() throws SQLException{
		PreparedStatement prst;
		prst=null;
	
		prst=MyMain.conne.conn.prepareStatement("Insert into facility values(?,?,?)");
		result(prst, 1, 0, 0, 0, 0, 100, 1);
		result(prst, 1, 0, 0, 0, 0, 60, 0);
		
		
	}
	
	public static void insertHotelFacility() throws SQLException{
		PreparedStatement prst,prst2=null;
		ResultSet rs=null;
		Vector<String> vec_namefacility=new Vector<String>();
		ZipfGenerator p=new ZipfGenerator(new Random(System.currentTimeMillis()));
		prst2 = MyMain.conne.conn.prepareStatement("SELECT \"nameFacility\" FROM facility WHERE \"nameFacility\" LIKE 'Hotel_facility%'");
		rs = prst2.executeQuery();
		while(rs.next()){	vec_namefacility.add(rs.getString(1));	}
		int cnt;
		prst = MyMain.conne.conn.prepareStatement("insert into hotelfacilities values (?,?,?)");
		
		for(int i=1;i<=100;i++){
			cnt= 10+ p.getRandInt(40);
			Vector<Integer> new_namefacility=new Vector<Integer>();
			for(int j=0;j<cnt;j++){
				new_namefacility.add(p.getRandInt(100));
				for(int k=0;k<j;k++){
					System.out.println("i="+i+" j="+j+" k="+k);
					if (new_namefacility.get(k).equals(new_namefacility.get(j))){
						new_namefacility.setElementAt(p.getRandInt(100), j);
						k=-1;
					}
				}
				prst.setInt(1, i);
				
				//System.out.println(temp_namefacility.get(j));
				String namefac= vec_namefacility.get(new_namefacility.get(j));
				
				prst.setString(2, namefac);
				prst.setString(3, "desc_"+namefac);
				prst.executeUpdate();
			}
		}
	}
	
	public static void insertRoomfacility() throws SQLException{
		PreparedStatement prst,prst2=null;
		ResultSet result;
		Vector<String> name=new Vector<String>();
		ZipfGenerator p=new ZipfGenerator(new Random(System.currentTimeMillis()));
		
		prst2 = MyMain.conne.conn.prepareStatement("SELECT \"nameFacility\" FROM facility WHERE \"nameFacility\" LIKE 'Room_facility%'");
		result= prst2.executeQuery();
		while(result.next()){	name.add(result.getString(1));	}
		int cnt;
		
		
		Vector<Integer> roomID=new Vector<Integer>();
		prst2 = MyMain.conne.conn.prepareStatement("SELECT \"idRoom\" FROM room");
		result= prst2.executeQuery();
		while(result.next()){	roomID.add(result.getInt(1));	}
		
		
		prst = MyMain.conne.conn.prepareStatement("insert into roomfacilities values (?,?,?)");
		for(int i=roomID.firstElement();i<=roomID.size();i++){
			cnt= p.getRandInt(6);
			Vector<Integer> new_namefacility=new Vector<Integer>();
			for(int j=0;j<cnt;j++){
				new_namefacility.add(p.getRandInt(name.size()));
				for(int k=0;k<j;k++){
					System.out.println("i="+i+" j="+j+" k="+k);
					if (new_namefacility.get(k).equals(new_namefacility.get(j))){
						new_namefacility.setElementAt(p.getRandInt(name.size()), j);
						
						k=-1;
					}
				}
				prst.setInt(1, i);
				
				//System.out.println(temp_namefacility.get(j));
				String namefac= name.get(new_namefacility.get(j));
				
				prst.setString(2, namefac);
				prst.setString(3, "desc_"+namefac);
				prst.executeUpdate();
			}
		}
	}
	/////////////////////////////////////////////////////////////////////////////////
	
	//////// 3
	
//	public static void erotima1() throws SQLException{
//		PreparedStatement prst = null;
//		ResultSet rs;
//		
//		prst = MyMain.conne.conn.prepareStatement("SELECT idHotel FROM hotelfacilities WHERE nameFacility= Hotel_facility_1_1_2_1_3");
//		rs = prst.executeQuery();
//		while(rs.next()){System.out.println(rs.ge);}	
//		
//	}
	
	public static void main(String[] args) throws SQLException {
		project4_U1 tu1 = new project4_U1();
		project4_U2 tu2 = new project4_U2();
		project4_U3 tu3 = new project4_U3();
		project4_U4 tu4 = new project4_U4();
		project4_Q1 tq1 = new project4_Q1();
		project4_Q2 tq2 = new project4_Q2();
		project4_Q3 tq3 = new project4_Q3();
		StandardInputRead input = new StandardInputRead();
		StandardInputRead readChoice= new StandardInputRead();	
		int choice=0;
		int hmeres_prosomoioshs;
		hmeres_prosomoioshs = input.readPositiveInt("dose hmeres prosomoiwshs");
		MyMain.login();
		MyMain.fillTheCities();
		MyMain.fillTheNames();
		
		PreparedStatement prst=null;
		ResultSet rs = null;
		/*MyMain.instertHotelTable();
		MyMain.insterRoomtypeTable();
		MyMain.insertRoomrateTable();
		MyMain.insertRoomTable();
		MyMain.insertTravelagentTable();
		MyMain.fillTheNames();
		MyMain.insertEmployee_Person();
		MyMain.insertClient_Person();
		MyMain.insertCreditCardTable();
		MyMain.insertFacility();*/
		//MyMain.insertHotelFacility();
		//MyMain.insertRoomfacility();
		//MyMain.erotima1();
		
		
		while (choice!=18){
			printMenu();
			choice= readChoice.readPositiveInt("Give your choice: ");               
			switch(choice){
			
			case 1:
				MyMain.fillTheCities();
				MyMain.instertHotelTable();
				break;
			case 2:
				MyMain.insterRoomtypeTable();
				break;
			case 3:
				MyMain.insertRoomrateTable();
				break;
			case 4:
				MyMain.insertRoomTable();
				break;
			case 5:
				MyMain.insertTravelagentTable();
				break;
			case 6:				
				MyMain.fillTheNames();
				MyMain.insertEmployee_Person();
				break;
			case 7:
				MyMain.insertClient_Person();
				break;
			case 8:
				MyMain.insertCreditCardTable();
			    break;
			case 9:
				MyMain.insertFacility();
				break;
			case 10:
				MyMain.insertHotelFacility();
				break;
			case 11:
				MyMain.insertRoomfacility();
				break;
			case 12:
				tu1.insertClient_Person(hmeres_prosomoioshs);
				tu1.insertCreditCardTable(hmeres_prosomoioshs);
				break;
			case 13:
				tu2.insert_Person(hmeres_prosomoioshs);
				break;
			case 14:
				prst = MyMain.conne.conn.prepareStatement(" select current_date;");
				rs = prst.executeQuery();
				while(rs.next()){
					trexousa_hmeromhnia = rs.getDate(1);
				}
				tu3.insert_hotelbooking_roombooking(trexousa_hmeromhnia,hmeres_prosomoioshs);
				break;
			case 15:
				tu4.update_hotelbooking_roombooking(hmeres_prosomoioshs);
				break;
			case 16:
				MyMain.clearDataBase();
				break;
			case 17:
				MyMain.fillDataBase();
				break;
			case 18:
				System.out.println("Bye Bye.");
				return;
			default:
				System.out.println("Wrong choice try again.");
				break;
			case 19:
				tq1.findPeople_Q1(hmeres_prosomoioshs);
				break;
			case 20:
				tq2.findPeople_Q2(hmeres_prosomoioshs);
				break;
			case 21:
				tq3.evresh_tzirou(hmeres_prosomoioshs);
				break;
			}
	}
		conne.close();
	}
	
	 public static void printMenu() {
			
			System.out.println("MENU: ");
			System.out.println("\t1)  instertHotelTable");
			System.out.println("\t2)  insterRoomtypeTable");
			System.out.println("\t3)  insertRoomrateTable");
			System.out.println("\t4)  insertRoomTable");
			System.out.println("\t5)  insertTravelagentTable");
			System.out.println("\t6)  insertEmployee_Person");
			System.out.println("\t7)  insertClient_Person");
			System.out.println("\t8)  insertCreditCardTable");
			System.out.println("\t9)  insertFacility");
			System.out.println("\t10)  insertHotelFacility");
			System.out.println("\t11)  insertRoomfacility");
			System.out.println("\t12)  U1");
			System.out.println("\t13)  U2");
			System.out.println("\t14)  U3");
			System.out.println("\t15)  U4");
			System.out.println("\t16)  clear DB");
			System.out.println("\t17)  fill DB");
			System.out.println("\t18)  exit");
			System.out.println("\t19)  Q1");
			System.out.println("\t20)  Q2");
			System.out.println("\t21)  Q3");
			
			
		}
	 public static void fillDataBase() throws SQLException{
		MyMain.instertHotelTable();
		MyMain.insterRoomtypeTable();
		MyMain.insertRoomrateTable();
		MyMain.insertRoomTable();
		MyMain.insertTravelagentTable();
		MyMain.fillTheNames();
		MyMain.insertEmployee_Person();
		//MyMain.insertClient_Person();
		//MyMain.insertCreditCardTable();
		MyMain.insertFacility();
		MyMain.insertHotelFacility();
		MyMain.insertRoomfacility();
		 
	 }
	 public static void clearDataBase() throws SQLException{
		  PreparedStatement pst;
		  pst= conne.conn.prepareStatement("Delete from roombooking");
		  pst.execute();
		  pst= conne.conn.prepareStatement("Delete from hotelbooking");
		  pst.execute();
		  pst= conne.conn.prepareStatement("Delete from roomfacilities");
		  pst.execute();
		  pst= conne.conn.prepareStatement("Delete from hotelfacilities");
		  pst.execute();
		  pst= conne.conn.prepareStatement("Delete from facility");
		  pst.execute();
		  pst= conne.conn.prepareStatement("Delete from creditcard");
		  pst.execute();
		  pst= conne.conn.prepareStatement("Delete from client");
		  pst.execute();
		  pst= conne.conn.prepareStatement("Delete from employee");
		  pst.execute();
		  pst= conne.conn.prepareStatement("Delete from person");
		  pst.execute();
		  pst= conne.conn.prepareStatement("Delete from room");
		  pst.execute();
		  pst= conne.conn.prepareStatement("Delete from roomrate");
		  pst.execute();
		  pst= conne.conn.prepareStatement("Delete from hotel");
		  pst.execute();
		  pst= conne.conn.prepareStatement("Delete from roomtype");
		  pst.execute();
		  pst= conne.conn.prepareStatement("Delete from travelagent");
		  pst.execute();
		  
		 }
	public static int result(PreparedStatement prst, int a0, int a1, int a2, int a3, int a4, int stop,int type) throws SQLException{
		String num, par_num = null;
		int i,j,k,l,m;
		int cnt=0;
		i=0;
		
		for(j = a1;j<=a1+4;j++){
			for (k = a2;k<=a2+3;k++){
				for(l = a3;l<=a3+2;l++){
					for(m = a4;m<=2;m++){
						
						if(i==0){
							num="1";
							i=1;
							par_num= "1";
						}else if(l==0){
							num=i+"_"+m;
							par_num= "1";
						}else if(k==0){
							num=i+"_"+m+"_"+l;
							par_num= i+"_"+m;
						}else if(j==0){
							num = i+"_"+m+"_"+l+"_"+k;
							par_num= i+"_"+m+"_"+l;
						}else{
							num = i+"_"+m+"_"+l+"_"+k+"_"+j;
							par_num= i+"_"+m+"_"+l+"_"+k;
						}
						if(type==1){
							//System.out.println("Hotel_facility"+testSt);
							prst.setString(1, "Hotel_facility_"+num);
							prst.setString(2, "Hotel_facility_"+par_num);
						}else{
							//System.out.println("Room_facility"+testSt);
							prst.setString(1, "Room_facility_"+num);
							prst.setString(2, "Room_facility_"+par_num);
						}
						prst.setString(3, "description_"+num);
						prst.executeUpdate();
						cnt++;
						//System.out.println(testSt+"\t"+cnt+"\t"+par_test);
						if(cnt==stop){
							//System.out.println("mpainei");
							return 1;
						}
					}
					a4=1;
					a3=1;
				}
				a2=1;
			}
			a1=1;
		}
		return 0;
	}
	
	
	
	public static void fillTheCities(){   //we fill the table cities with 10 cities 4 from Brasil 4 from Egypt and 2 from Thailand
		//brasil
		cities[0]="Sao Paulo";
		cities[1]="Porto Velho";
		cities[2]="Miracema do Norte";
		cities[3]="Cuiaba";
		//Egypt
		cities[4]="Cairo";
		cities[5]="Al Madiq";
		cities[6]="Asyut";
		cities[7]="Sohag";
		//Thailand
		cities[8]="Bangkok";
		cities[9]="Pattaya";
	}
	
	public static void fillTheNames(){
		
		for(int i=0;i<=199;i++){
			surnames[i] = "Surname_"+i;
		}
		for(int i=0;i<=49;i++){
			names[i] = "Name_"+i;
		}
	}
}