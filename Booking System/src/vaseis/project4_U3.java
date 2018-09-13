package vaseis;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;

public class project4_U3 {
	static Vector<Integer> person_id = new Vector<Integer>();
	static Vector<Integer> hotel_ID = new Vector<Integer>();
	static Vector<Integer> roomid_free_ofhotel = new Vector<Integer>();
	static Vector<Integer> clients_id = new Vector <Integer>();
	
	public static void insert_hotelbooking_roombooking(Date trexousa_hmeromhnia,int hmeres_prosomoioshs	) throws SQLException{
		int temp = 0;
		int ari8mos_domation_krathsh = 0;
		int diarkeia_diamonis = 0;
		Random RandomNum = new Random();
		PreparedStatement prst,prst1,prst2,prst3,prst4, prst5,prst6,prst7=null;
		ResultSet rs,rs1,rs2,rs3,rs4,rs6,rs7;
		int ari8mos_clients = 0 ;    //o ari8mos ton clients pou exei i vasi mas
		int ari8mos_krathseon=0;
		int CLIENT_ID,HOTEL_ID	 = 0 ;
		float totalamount,rate=0;
		int lastID=0;
		long timebefore = 0;
		long timeafter = 0;
		long measurement = 0;
		
		for(int hmera=0;hmera<hmeres_prosomoioshs;hmera++){
			
			prst = MyMain.conne.conn.prepareStatement(" select current_date +"+hmera);
			
			timebefore = System.currentTimeMillis();
			rs = prst.executeQuery();
			timeafter = System.currentTimeMillis();
			measurement = measurement + (timeafter - timebefore);
			
			while(rs.next()){
				trexousa_hmeromhnia = rs.getDate(1);
			}
			
			//hotel_ID.clear();
			//trexousa_hmeromhnia.setDate(trexousa_hmeromhnia.getDate()+hmera);
			prst2=MyMain.conne.conn.prepareStatement(" select count( \"idClient\") from client");
			
			timebefore = System.currentTimeMillis();
			rs2 = prst2.executeQuery();
			timeafter = System.currentTimeMillis();
			measurement = measurement + (timeafter - timebefore);
			
			while(rs2.next()){
				ari8mos_clients = rs2.getInt(1);
			}
			prst3=MyMain.conne.conn.prepareStatement(" select  \"idClient\" from client");
			
			timebefore = System.currentTimeMillis();
			rs3 = prst3.executeQuery();
			timeafter = System.currentTimeMillis();
			measurement = measurement + (timeafter - timebefore);
			
			while(rs3.next()){
				clients_id.add(rs3.getInt(1));//ola ta clients id 
			}
			
			ari8mos_krathseon = (int) (0.2 * ari8mos_clients);//to 20% ton eggegramenon pelaton
			prst4=MyMain.conne.conn.prepareStatement("SELECT \"idHotel\" FROM hotel");
			
			timebefore = System.currentTimeMillis();
			rs1 = prst4.executeQuery();
			timeafter = System.currentTimeMillis();
			measurement = measurement + (timeafter - timebefore);
			
			while(rs1.next()){
				hotel_ID.add(rs1.getInt(1));
			}
			
			prst6 = MyMain.conne.conn.prepareStatement("select \"idPerson\" from person");
			
			timebefore = System.currentTimeMillis();
			rs6 = prst6.executeQuery();
			timeafter = System.currentTimeMillis();
			measurement = measurement + (timeafter - timebefore);
			
			while (rs6.next()){
				person_id.add(rs6.getInt(1));
			}
			
	
			for (int i=0;i<ari8mos_krathseon;i++){
				roomid_free_ofhotel.clear();
				totalamount = 0;
				
				CLIENT_ID=clients_id.get(RandomNum.nextInt(clients_id.size()));
				HOTEL_ID = hotel_ID.get(RandomNum.nextInt(hotel_ID.size()));
				
				temp = RandomNum.nextInt(10);
				if(temp <=4){
					ari8mos_domation_krathsh =1;
				}else if(temp <=6){
					ari8mos_domation_krathsh =2;
				}else ari8mos_domation_krathsh =3;
				temp = RandomNum.nextInt(10);
				if(temp <= 3){
					diarkeia_diamonis = 1;
				}else if(temp <= 6){
					diarkeia_diamonis = 2;
				}else if(temp == 7){
					diarkeia_diamonis = 3;
				}else if(temp == 8){
					diarkeia_diamonis = 4;
				}else if(temp <= 9){
						diarkeia_diamonis = 5;
				}
				prst2=null;
				rs2=null;
				prst2 = MyMain.conne.conn.prepareStatement("select \"idRoom\" from room where room.vacant=1 and room.\"hotelID\"="+HOTEL_ID);
				
				timebefore = System.currentTimeMillis();
				rs2=prst2.executeQuery();
				timeafter = System.currentTimeMillis();
				measurement = measurement + (timeafter - timebefore);
				
				while(rs2.next()){
					roomid_free_ofhotel.add(rs2.getInt(1));
				}
				
				if (roomid_free_ofhotel.size() >= ari8mos_domation_krathsh){
					prst2=null;
					roomid_free_ofhotel.clear();
					rs2=null;
					prst2 = MyMain.conne.conn.prepareStatement("select \"idRoom\" from room where room.vacant=1 and room.\"hotelID\"="+HOTEL_ID+" LIMIT "+ari8mos_domation_krathsh);
					
					timebefore = System.currentTimeMillis();
					rs2=prst2.executeQuery();
					timeafter = System.currentTimeMillis();
					measurement = measurement + (timeafter - timebefore);
					
					while(rs2.next()){
						roomid_free_ofhotel.add(rs2.getInt(1));
					}
					
					for(int i1=0;i1<roomid_free_ofhotel.size();i1++){
						prst3=null;
						rs3=null;
						String roomtype = null;
						prst3 = MyMain.conne.conn.prepareStatement("select roomtype from room where room.\"idRoom\" ="+roomid_free_ofhotel.get(i1));
						
						timebefore = System.currentTimeMillis();
						rs3 = prst3.executeQuery();
						timeafter = System.currentTimeMillis();
						measurement = measurement + (timeafter - timebefore);
						
						while(rs3.next()){
							roomtype = rs3.getString(1);
						}
						prst2 = null;
						rs2=null;
						prst2 = MyMain.conne.conn.prepareStatement("select rate from roomrate where roomrate.\"roomtype\"='"+roomtype+"' and roomrate.\"hotelID\" ="+HOTEL_ID );
						
						timebefore = System.currentTimeMillis();
						rs2=prst2.executeQuery();
						timeafter = System.currentTimeMillis();
						measurement = measurement + (timeafter - timebefore);
						
						while(rs2.next()){
							totalamount = totalamount + rs2.getFloat(1);
						}
					}
						//System.out.println("total amount is ="+totalamount);
						prst1=MyMain.conne.conn.prepareStatement("insert into hotelbooking values(?,?,?,?,?,?,?)");
						prst7 = MyMain.conne.conn.prepareStatement("SELECT \"idhotelbooking\" FROM hotelbooking ORDER BY \"idhotelbooking\" DESC limit 1");
						
						timebefore = System.currentTimeMillis();
						rs7 = prst7.executeQuery();
						timeafter = System.currentTimeMillis();
						measurement = measurement + (timeafter - timebefore);
						
						while(rs7.next()){
							lastID = rs7.getInt(1);
						}
						prst1.setInt(1,lastID+1 );
						prst1.setDate(2,trexousa_hmeromhnia );
						prst1.setNull(3,0);
						prst1.setFloat(4,totalamount );
						prst1.setFloat(5,totalamount );
						prst1.setInt(6, 1);
						prst1.setInt(7,CLIENT_ID );
						
						timebefore = System.currentTimeMillis();
						prst1.executeUpdate();
						timeafter = System.currentTimeMillis();
						measurement = measurement + (timeafter - timebefore);
						
						for(int i1=0;i1<roomid_free_ofhotel.size();i1++){
							
							prst3=null;
							rs3=null;
							String roomtype = null;
							prst3 = MyMain.conne.conn.prepareStatement("select roomtype from room where room.\"idRoom\" ="+roomid_free_ofhotel.get(i1));
							
							timebefore = System.currentTimeMillis();
							rs3 = prst3.executeQuery();
							timeafter = System.currentTimeMillis();
							measurement = measurement + (timeafter - timebefore);
							
							while(rs3.next()){
								roomtype = rs3.getString(1);
							}
							prst2 = null;
							rs2=null;
							prst2 = MyMain.conne.conn.prepareStatement("select rate from roomrate where roomrate.\"roomtype\"='"+roomtype+"' "
									+ "and roomrate.\"hotelID\" ="+HOTEL_ID );
							
							timebefore = System.currentTimeMillis();
							rs2=prst2.executeQuery();
							timeafter = System.currentTimeMillis();
							measurement = measurement + (timeafter - timebefore);
							
							while(rs2.next()){
								rate = rs2.getFloat(1);
							}
							prst4=null;
							rs4=null;
							prst4 = MyMain.conne.conn.prepareStatement("insert into roombooking values(?,?,?,?,?,?,?)");
//							
							prst4.setInt(1,lastID+1);
							prst4.setInt(2, roomid_free_ofhotel.get(i1));
							prst4.setInt(3,person_id.get(RandomNum.nextInt(person_id.size())));
							prst4.setDate(4, trexousa_hmeromhnia);
							Date checkout=null;
							prst=null;
							rs=null;
							temp = diarkeia_diamonis + hmera;
							prst = MyMain.conne.conn.prepareStatement(" select current_date  +"+temp);
							
							timebefore = System.currentTimeMillis();
							rs = prst.executeQuery();
							timeafter = System.currentTimeMillis();
							measurement = measurement + (timeafter - timebefore);
							
							while(rs.next()){
								prst4.setDate(5, rs.getDate(1)); //checkout
							}
							prst4.setNull(6,0);
							prst4.setDouble(7, rate);
							
							timebefore = System.currentTimeMillis();
							prst4.executeUpdate();
							timeafter = System.currentTimeMillis();
							measurement = measurement + (timeafter - timebefore);
							
							prst = null;
							prst= MyMain.conne.conn.prepareStatement("update room set vacant = 0 where room.\"idRoom\" ="+roomid_free_ofhotel.get(i1));
							
							timebefore = System.currentTimeMillis();
							prst.executeUpdate();
							timeafter = System.currentTimeMillis();
							measurement = measurement + (timeafter - timebefore);
						}
					
				}
				
			}

		}
		System.out.println("U3 measurement is: "+measurement+" for "+hmeres_prosomoioshs+" days" );
	}
	
}

