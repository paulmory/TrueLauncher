/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
 */

package truelauncher.client.auth;

import java.io.DataOutputStream;
import java.net.Socket;

import truelauncher.utils.LauncherUtils;

public class Auth {
	
	public static void sendAuth1(final String hostname, final int port, final int protocolversion, final String nick, final String token, final String password)
	{
		new Thread()
		{
			public void run()
			{
				try {
					//establish connection
					Socket socket = new Socket(hostname, port);
					socket.setSoTimeout(30000);
					socket.setTrafficClass(24);
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					//write handshake packet
					Type1.writeAuthPacket(dos, port, protocolversion, nick, token, password);
					//close socket
					socket.close();	
				} catch (Exception e) {
					LauncherUtils.logError(e);
				}
			}
		}.start();
	}

	public static void sendAuth2(final String hostname, final int port, final int protocolversion, final String nick, final String token, final String password)
	{
		new Thread()
		{
			public void run()
			{
				try {
					//
					//fake 1.7.2 handshake packet chages format.
					//host = authpacket("AuthConnector"+ nick + token + password)
					//
					//establish connection
					Socket socket = new Socket(hostname, port);
					socket.setSoTimeout(30000);
					socket.setTrafficClass(24);
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					//write handshake packet
					Type2.writeAuthPacket(dos, port, protocolversion, nick, token, password);
					//close socket
					socket.close();
				} catch (Exception e) {
					LauncherUtils.logError(e);
				}
			}
		}.start();
	}

	
}
