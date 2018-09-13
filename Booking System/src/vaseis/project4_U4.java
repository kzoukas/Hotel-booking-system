package vaseis;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;

public class project4_U4 {
	static Vector<Integer> hotelBooking_id_fromRoombooking = new Vector<Integer>();
	
	public static void update_hotelbooking_roombooking(int hmeres_prosomoioshs) throws SQLException{
		PreparedStatement prst=null;
		ResultSet rs=null;
		int num_cancel=0;
		int bookid_toCancel=0;
		Random RandomNum = new Random();
		long timebefore = 0;
		long timeafter = 0;
		long measurement = 0;
		
		for(int hmera=0;hmera<hmeres_prosomoioshs;hmera++){
			prst=MyMain.conne.conn.prepareStatement("select \"hotelbookingID\" from roombooking where (roombooking.cancelationdate is null and "
					+ "roombooking.checkin != (select current_date+"+hmera+"))"); 
			
			timebefore = System.currentTimeMillis();
			rs = prst.executeQuery();
			timeafter = System.currentTimeMillis();
			measurement = measurement + (timeafter - timebefore);
			
			while(rs.next()){
				hotelBooking_id_fromRoombooking.add(rs.getInt(1));
			}
			num_cancel = (int) (hotelBooking_id_fromRoombooking.size() * 0.2);
			for (int i=0; i<num_cancel; i++){
				bookid_toCancel = hotelBooking_id_fromRoombooking.get(RandomNum.nextInt(hotelBooking_id_fromRoombooking.size()));
				prst = null;
				rs = null;
				prst=MyMain.conne.conn.prepareStatement("delete from roombooking where roombooking.\"hotelbookingID\"="+bookid_toCancel+"and "
						+ "(roombooking.\"roomID\" = (select \"roomID\" from roombooking where roombooking.\"hotelbookingID\" ="+bookid_toCancel+"limit 1))");
				
				timebefore = System.currentTimeMillis();
				prst.executeUpdate();
				timeafter = System.currentTimeMillis();
				measurement = measurement + (timeafter - timebefore);
				
				prst = null;
				prst=MyMain.conne.conn.prepareStatement("delete from hotelbooking where hotelbooking.\"idhotelbooking\"in"
						+ "( select \"idhotelbooking\" from hotelbooking where hotelbooking.\"idhotelbooking\" not in (select \"hotelbookingID\" from roombooking))");
				
				timebefore = System.currentTimeMillis();
				prst.executeUpdate();
				timeafter = System.currentTimeMillis();
				measurement = measurement + (timeafter - timebefore);
				
			}
		}
		System.out.println("U4 measurement is: "+measurement+" for "+hmeres_prosomoioshs+" days" );
	}
}
