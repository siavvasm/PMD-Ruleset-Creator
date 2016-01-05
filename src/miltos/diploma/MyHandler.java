package miltos.diploma;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Vector;

/**
 * 	The default handler of the XML parser that is used to import the relusets.
 * 	Static List and Vector objects are used to store the names of the rulesets
 *  and their corresponding rules. This vectors are used by RulesetCreator to
 *  instantiate the desired ComboBox objects for the app's GUI.
 *  
 *  Possible improvements:
 *  
 *  	- 	Remove static identifier from ruleSetList, ruleListVec and ruleList.
 *  		Create a method that will return those objects to RulesetCreator.
 *  		The method should be invoked when "</rulesets>" is found in the xml 
 *  		file.
 * 
 *
 */
public class MyHandler extends DefaultHandler {
	
	boolean ruleset = false;								//A flag stating that <ruleset> tag is found in the document.
	boolean rule = false;									//A flag stating that <rule> tag is found in the document.
	static ArrayList<String> ruleSetList = null;			//An arrayList for holding the names of the rulesets found in the xml file.
	static Vector<String> ruleList = null;					//A vector for holding the names of the rules of the currently processed ruleset.
	static Vector<Vector<String>> ruleListVec = null;		//A vector holding the vectors of the rules that belong to each ruleset found in the xml file.
	Vector<String> tempRuleList = null;
	//static int i;
	//qName = it contains the name of the element found in the document. e.g. qName = rule -> <rule>
	
	/**
	 *  The constructor of the handler. It just creates the three basic static objects
	 *  that are used to store the names of the rulesets and their corresponding rules.
	 */
	public MyHandler(){
		ruleList = new Vector<String>();
		ruleList.add("");
		ruleSetList = new ArrayList<String>();
		ruleListVec = new Vector<Vector<String>>();
	}
	
	/**
	 * The method that is invoked every time the begining of an element is found in the
	 * xml file.
	 */
	public void startElement(String uri, String localName,String qName, 
            Attributes attributes) throws SAXException {
		
		System.out.println("Start Element : " + qName);
		
		if("ruleset".equalsIgnoreCase(qName)){
			ruleset = true;									// State that <ruleset> tag is just found in the document
			ruleSetList.add(attributes.getValue(0));
		}
		
		if("rule".equalsIgnoreCase(qName)){
			rule = true;									// State that <rule> tag is just found in the document
		}
	}
	
	/**
	 * The method that is invoked every time the end of an element is found in the xml file.
	 */
	public void endElement(String uri, String localName,
			String qName) throws SAXException {

			System.out.println("End Element : " + qName);
			
			if("ruleset".equalsIgnoreCase(qName)){
				tempRuleList = (Vector)ruleList.clone();
				ruleListVec.add(tempRuleList);
				//System.out.println("[HANDLER] SIZEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE" + ruleListVec.get(i).size() );
				//i++;
				ruleList.removeAllElements();
				ruleList.add("");
			}

	}
	
	/**
	 * The method for collecting the info that is included between the starting and
	 * ending tag of an element.
	 */
	public void characters(char ch[], int start, int length) throws SAXException {
		
		/* 
		 * If the starting tag <rule> is found in the document.
		 */
		if (rule) {
			System.out.println("Rule Name : " + new String(ch, start, length));
			ruleList.add(new String(ch, start, length));
			rule = false;										
		}

		/* 
		 * If the starting tag <ruleset> is found in the document.
		 */
		if (ruleset) {
			ruleset = false;
		}
		

	}
	

}
