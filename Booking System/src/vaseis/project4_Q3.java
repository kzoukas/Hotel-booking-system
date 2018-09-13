package vaseis;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class project4_Q3 {
	
	public static void evresh_tzirou(int hmeres_prosomoioshs) throws SQLException{
		PreparedStatement prst = null;
		ResultSet rs=null;
		Random RandomNum = new Random();
		String typename;
		long timebefore = 0;
		long timeafter = 0;
		long measurement = 0;
		
		for(int hmera=0;hmera<hmeres_prosomoioshs;hmera++){
			typename = "typename_" +(RandomNum.nextInt(10)+1);
			prst=MyMain.conne.conn.prepareStatement("select sum(rate) from roombooking where roombooking.\"roomID\" in "
					+ "(select \"idRoom\" from room where room.roomtype = '"+typename+"')");
			
			timebefore = System.currentTimeMillis();
			rs = prst.executeQuery();
			timeafter = System.currentTimeMillis();
			measurement = measurement + (timeafter - timebefore);
			while (rs.next()){
				System.out.println("o tziros tou "+typename+" einai "+rs.getInt(1));
			}
		}
		System.out.println("Q3 measurement is: "+measurement+" for "+hmeres_prosomoioshs+" days" );
	}
}
