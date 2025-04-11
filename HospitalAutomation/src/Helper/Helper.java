package Helper;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Helper {
	
	public static void optionPaneChangeTR() {
		UIManager.put("OptionPane.cancelButtonText", "İptal");
		UIManager.put("OptionPane.okButtonText", "Tamam");
		UIManager.put("OptionPane.yesButtonText", "Evet");
		UIManager.put("OptionPane.noButtonText", "Hayır");
	}
	
	public static void showMsg(String str) {
		String msg;
		optionPaneChangeTR();
		switch (str) {
			case "fill":
				msg = "Lütfen boş alanları doldurunuz!";
				break;
			case "success":
				msg = "İşlem başarılı!";
				break;
			case "error":
				msg = "İşlem hatası! Lütfen kontrol ediniz!";
				break;
			default:
				msg = str;
		}
		JOptionPane.showMessageDialog(null, msg, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static boolean confirm(String str) {
		String msg;
		optionPaneChangeTR();
		switch (str) {
		case "sure":
			msg = "Bu işlemi gerçekleştirmek istediğine emin misin?";
			break;
		default:
			msg = str;
			break;
		}
		
		int result = JOptionPane.showConfirmDialog(null, msg, "Dikkat!", JOptionPane.YES_NO_OPTION);
		
		if(result == 0) {
			return true;
		} else {
			return false;
		}
	}
}
