package vaseis;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class project4_Q2 {
	static Vector<Integer> personTwiceOrMoreInRoombooking = new Vector<Integer>();
	
	public static void findPeople_Q2(int hmeres_prosomoioshs) throws SQLException{
		PreparedStatement prst,prst1, prst2=null;
		ResultSet rs,rs1,rs2=null;
		long timebefore = 0;
		long timeafter = 0;
		long measurement = 0;
		for(int hmera=0;hmera<hmeres_prosomoioshs;hmera++){
			prst =MyMain.conne.conn.prepareStatement("select  \"bookedforpersonID\" from roombooking  "
					+ "where roombooking.\"bookedforpersonID\" in "
					+ "(select \"bookedforpersonID\" from roombooking group by \"bookedforpersonID\" having count(*)>=2)");
			
			
			timebefore = System.currentTimeMillis();
			rs = prst.executeQuery();
			timeafter = System.currentTimeMillis();
			measurement = measurement + (timeafter - timebefore);
			
			while(rs.next()){
				personTwiceOrMoreInRoombooking.add(rs.getInt(1));
			}
			for(int i=0;i<personTwiceOrMoreInRoombooking.size();i++){
				prst =MyMain.conne.conn.prepareStatement("select  checkout from roombooking  "
						+ "where roombooking.\"bookedforpersonID\" = "+personTwiceOrMoreInRoombooking.get(i)+" ");
				
				timebefore = System.currentTimeMillis();
				rs = prst.executeQuery();
				timeafter = System.currentTimeMillis();
				measurement = measurement + (timeafter - timebefore);
				
				int temp =i+1;
				prst1 = MyMain.conne.conn.prepareStatement("select   checkin from roombooking  "
					+ "where roombooking.\"bookedforpersonID\"= "+personTwiceOrMoreInRoombooking.get(i)+"limit 1 offset 1 ");
					
				
				timebefore = System.currentTimeMillis();
				rs1 = prst1.executeQuery();
				timeafter = System.currentTimeMillis();
				measurement = measurement + (timeafter - timebefore);
				
				while (rs.next() && rs1.next()){
					prst2 = MyMain.conne.conn.prepareStatement("select 1 where "+rs1.getDate(1)+"<="+rs.getDate(1)+" ");
					
					timebefore = System.currentTimeMillis();
					rs2 = prst2.executeQuery();
					timeafter = System.currentTimeMillis();
					measurement = measurement + (timeafter - timebefore);
					
					while(rs2.next()){
						if (rs2.getInt(1)==1){
							System.out.println(personTwiceOrMoreInRoombooking.get(i));
						}
					}
				}
			}
		}
		System.out.println("Q2 measurement is: "+measurement);
	}

}
