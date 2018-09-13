package vaseis;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

public class project4_Q1 {
	static Vector<Integer> clients_id = new Vector <Integer>();
	
	public static void findPeople_Q1(int hmeres_prosomoioshs) throws SQLException{
		Random RandomNum = new Random();
		String CITY;
		PreparedStatement prst = null;
		ResultSet rs=null;
		int CLIENT;
		String CHECKIN, CHECKOUT;
		long timebefore = 0;
		long timeafter = 0;
		long measurement = 0;
		
		for(int hmera=0;hmera<hmeres_prosomoioshs;hmera++){
			CITY = MyMain.cities[RandomNum.nextInt(10)];   //h sugkekrimenh poli 
			
			prst=MyMain.conne.conn.prepareStatement(" select  \"idClient\" from client");
			
			timebefore = System.currentTimeMillis();
			rs = prst.executeQuery();
			timeafter = System.currentTimeMillis();
			measurement = measurement + (timeafter - timebefore);
			
			while(rs.next()){
				clients_id.add(rs.getInt(1));//ola ta clients id 
			}
			CLIENT = clients_id.get(RandomNum.nextInt(clients_id.size())); 
			CLIENT = 99003;
			CHECKIN = "2014-05-31";
			CHECKOUT = "2014-05-31";
			CITY = "Cairo";
			prst = null;
			rs = null;
			prst=MyMain.conne.conn.prepareStatement("select \"bookedforpersonID\" from roombooking  where roombooking.checkin = '"+CHECKIN+"' and "
					+ "roombooking.checkout = '"+CHECKOUT+"' and roombooking.\"hotelbookingID\" in"
					+ "(select  \"idhotelbooking\" from hotelbooking where hotelbooking.\"bookedbyClientID\" = "+CLIENT+") and "
					+ "roombooking.\"bookedforpersonID\" in (select  \"idPerson\" from person where person.city = '"+CITY+"');");
			
			timebefore = System.currentTimeMillis();
			rs = prst.executeQuery();
			timeafter = System.currentTimeMillis();
			measurement = measurement + (timeafter - timebefore);
			
			while(rs.next()){
				System.out.println(rs.getInt(1));
			}
		}
		System.out.println("Q1  measurement is: "+measurement+" for "+hmeres_prosomoioshs+" days" );
	}

}
