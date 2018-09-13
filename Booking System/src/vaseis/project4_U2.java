package vaseis;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class project4_U2 {
	static ZipfGenerator zipf=new ZipfGenerator(new Random(System.currentTimeMillis()));
	
	
	public static void insert_Person(int hmeres_prosomoioshs) throws SQLException{
		Random RandomNum = new Random();
		PreparedStatement prst,prst1 = null;
		ResultSet rs,rs1;
		int lastID =0;
		int ari8mos_person = 0 ;
		long timebefore = 0;
		long timeafter = 0;
		long measurement = 0;
		
		for (int j=0;j<hmeres_prosomoioshs;j++){
			prst=MyMain.conne.conn.prepareStatement(" select count( \"idClient\") from client");
			
			timebefore = System.currentTimeMillis();
			rs = prst.executeQuery();
			timeafter = System.currentTimeMillis();
			measurement = measurement + (timeafter - timebefore);
			
			while(rs.next()){
				ari8mos_person = rs.getInt(1) * 5/100;
			}
			for(int k=0;k<ari8mos_person;k++) {
				prst = MyMain.conne.conn.prepareStatement("insert into person values(?,?,?,?,?,?,?,?)");
				prst1 = MyMain.conne.conn.prepareStatement("SELECT \"idPerson\" FROM person ORDER BY \"idPerson\" DESC limit 1");
				
				timebefore = System.currentTimeMillis();
				rs1 = prst1.executeQuery();
				timeafter = System.currentTimeMillis();
				measurement = measurement + (timeafter - timebefore);
				
				while(rs1.next()){
					lastID = rs1.getInt(1);
				}
				prst.setInt(1, lastID +1);
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
				
				prst.setString(8, "Person_Address_"+k);
				
				timebefore = System.currentTimeMillis();
				prst.executeUpdate();
				timeafter = System.currentTimeMillis();
				measurement = measurement + (timeafter - timebefore);
			}
		}
		System.out.println("U2 measurement is: "+measurement+" for "+hmeres_prosomoioshs+" days" );
	}
}
