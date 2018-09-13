package vaseis;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import vaseis.ZipfGenerator;

public class project4_U1 {
	static ZipfGenerator zipf=new ZipfGenerator(new Random(System.currentTimeMillis()));
	static long measurement = 0;
	
	public static void insertClient_Person(int hmeres_prosomoioshs) throws SQLException{ //code in idPerson for Client 
		PreparedStatement prst = null;
		PreparedStatement prst2 = null;
		ResultSet rs;
		Random RandomNum = new Random();
		int documentClient=100000000;
		long timebefore = 0;
		long timeafter = 0;
		int lastID=0;
		
		for(int k=0;k<hmeres_prosomoioshs;k++){
			for(int i=1;i<=100;i++){
				prst = MyMain.conne.conn.prepareStatement("insert into person values(?,?,?,?,?,?,?,?)");
				prst2 = MyMain.conne.conn.prepareStatement("SELECT \"idPerson\" FROM person  ORDER BY \"idPerson\"  DESC limit 1");
				rs = prst2.executeQuery();
				while(rs.next()){
					lastID = rs.getInt(1);
				}
				prst.setInt(1,lastID + 1);
				int selectName = zipf.zipf(50);
				prst.setString(2, MyMain.names[selectName]);
				selectName = zipf.zipf(200);
				prst.setString(3, MyMain.surnames[selectName]);
				prst.setInt(4, RandomNum.nextInt(2));
				prst.setDate(5,null );
				int zipf_city = zipf.zipf(10);	
				prst.setString(6, MyMain.cities[zipf_city]);
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
				
				timebefore = System.currentTimeMillis();
				prst.executeUpdate();
				timeafter = System.currentTimeMillis();
				measurement = measurement + (timeafter - timebefore);
				
				prst2 = MyMain.conne.conn.prepareStatement("insert into client values (?,?)");
				
				prst2.setInt(1,lastID + 1);
				prst2.setString(2,"ID_"+documentClient+i+k*100);
				
				timebefore = System.currentTimeMillis();
				prst2.executeUpdate();
				timeafter = System.currentTimeMillis();
				measurement = measurement + (timeafter - timebefore);
			}
		}		
	}
	
	public static void insertCreditCardTable(int hmeres_prosomoioshs) throws SQLException{
		Random RandomNum = new Random();
		int numberOfcreditCards;
		PreparedStatement prst = null;
		int credicardNum = 1000;
		long timebefore = 0;
		long timeafter = 0;
		
		for(int k=0;k<hmeres_prosomoioshs;k++){
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
						prst.setString(4, MyMain.names[selectName] +" "+ MyMain.surnames[selectSurName]);
						prst.setInt(5, 100*k+i);
						
						timebefore = System.currentTimeMillis();
						prst.executeUpdate();
						timeafter = System.currentTimeMillis();
						measurement = measurement + (timeafter - timebefore);
					}
				}
				
			}
		}
		System.out.println("U1 measurement is: "+measurement+" for "+hmeres_prosomoioshs+" days" );
		
		
	}

}
