package miltos.diploma;

import java.io.*;
import java.net.*;
import java.awt.event.WindowListener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.lang.Thread;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.Line;

public class RulesetCreator  extends Frame  implements WindowListener,ActionListener   {
	
/* Declare the GUI Components */
static JFrame frame;				// The frame that implements the app's GUI
static TextField rulesetName;		// The text field needed to type the exact ruleset name
static TextField specificRuleName;  // The text field needed to type the specific rule, of the selected ruleset, that will be inclued
static TextField excludeRule;		// The exact name of the rule that should be excluded
static TextField fileName;			// The text field for providing the file name of the ruleset.
static JTextArea console;			// A text area to print messages to the user
static JButton create;				// A button for creating a new ruleset.
static JButton save;				// A button for saving the ruleset.
static JButton add;					// A button for adding the new rule to the ruleset.
BufferedWriter writer;

/* List Feature */
static JComboBox rulesets;
static JComboBox rules;
static  String[] availRuleSets;
static DefaultComboBoxModel[] comboModels; 
static JButton addFromList;


	public static void initComboBoxes(){
		String[] availRuleSets = {"android","basic","braces","clone","codesize","comments","controversial","coupling","design","empty","finalizers","imports","j2ee","javabeans","junit","logging-jakarta-commons","logging-java","migrating","naming","optimizations","strictexception","strings","sunsecure","typeresolution","unnecessary","unusedcode","unusedcode", "imports"};
		comboModels = new DefaultComboBoxModel[26];
		comboModels[0] = new DefaultComboBoxModel(new String[]{"","CallSuperFirst","CallSuperLast","DoNotHardCodeSDCard"});
		comboModels[1] = new DefaultComboBoxModel(new String[]{"","JumbledIncrementer","ForLoopShouldBeWhileLoop","OverrideBothEqualsAndHashcode","DoubleCheckedLocking","ReturnFromFinallyBlock","UnconditionalIfStatement","BooleanInstantiation","CollapsibleIfStatements","ClassCastExceptionWithToArray","AvoidDecimalLiteralsInBigDecimalConstructor","MisplacedNullCheck","AvoidThreadGroup","BrokenNullCheck","BigIntegerInstantiation","AvoidUsingOctalValues","AvoidUsingHardCodedIP","CheckResultSet","AvoidMultipleUnaryOperators","ExtendsObject","CheckSkipResult","AvoidBranchingStatementAsLastInLoop","DontCallThreadRun","DontUseFloatTypeForLoopIndices","SimplifiedTernary"});
		comboModels[2] = new DefaultComboBoxModel(new String[]{"","IfStmtsMustUseBraces","WhileLoopsMustUseBraces","IfElseStmtsMustUseBraces","ForLoopsMustUseBraces"});
		comboModels[3] = new DefaultComboBoxModel(new String[]{"","ProperCloneImplementation","CloneThrowsCloneNotSupportedException","CloneMethodMustImplementCloneable","CloneMethodReturnTypeMustMatchClassName","CloneMethodMustBePublic"});
		comboModels[4] = new DefaultComboBoxModel(new String[]{"","NPathComplexity","ExcessiveMethodLength","ExcessiveParameterList","ExcessiveClassLength","CyclomaticComplexity","StdCyclomaticComplexity","ModifiedCyclomaticComplexity","ExcessivePublicCount","TooManyFields","NcssMethodCount","NcssTypeCount","NcssConstructorCount","TooManyMethods"});
		comboModels[5] = new DefaultComboBoxModel(new String[]{"","CommentRequired","CommentSize","CommentContent","CommentDefaultAccessModifier"});
		comboModels[6] = new DefaultComboBoxModel(new String[]{"","UnnecessaryConstructor","NullAssignment","OnlyOneReturn","AssignmentInOperand","AtLeastOneConstructor","DontImportSun","SuspiciousOctalEscape","CallSuperInConstructor","UnnecessaryParentheses","DefaultPackage","DataflowAnomalyAnalysis","AvoidFinalLocalVariable","AvoidUsingShortType","AvoidUsingVolatile","AvoidUsingNativeCode","AvoidAccessibilityAlteration","DoNotCallGarbageCollectionExplicitly","OneDeclarationPerLine","AvoidPrefixingMethodParameters","AvoidLiteralsInIfCondition","UseObjectForClearerAPI","UseConcurrentHashMap"});
		comboModels[7] = new DefaultComboBoxModel(new String[]{"","CouplingBetweenObjects","ExcessiveImports","LooseCoupling","LoosePackageCoupling","LawOfDemeter"});
		comboModels[8] = new DefaultComboBoxModel(new String[]{"","UseSingleton","UseUtilityClass","SimplifyBooleanReturns","SimplifyBooleanExpressions","SwitchStmtsShouldHaveDefault","AvoidDeeplyNestedIfStmts","AvoidReassigningParameters","SwitchDensity","ConstructorCallsOverridableMethod","AccessorClassGeneration","FinalFieldCouldBeStatic","CloseResource","NonStaticInitializer","DefaultLabelNotLastInSwitchStmt","NonCaseLabelInSwitchStatement","OptimizableToArrayCall","BadComparison","EqualsNull","ConfusingTernary","InstantiationToGetClass","IdempotentOperations","SimpleDateFormatNeedsLocale","ImmutableField","UseLocaleWithCaseConversions","AvoidProtectedFieldInFinalClass","AssignmentToNonFinalStatic","MissingStaticMethodInNonInstantiatableClass","AvoidSynchronizedAtMethodLevel","MissingBreakInSwitch","UseNotifyAllInsteadOfNotify","AvoidInstanceofChecksInCatchClause","AbstractClassWithoutAbstractMethod","SimplifyConditional","CompareObjectsWithEquals","PositionLiteralsFirstInComparisons","PositionLiteralsFirstInCaseInsensitiveComparisons","UnnecessaryLocalBeforeReturn","NonThreadSafeSingleton","SingleMethodSingleton","SingletonClassReturningNewInstance","UncommentedEmptyMethodBody","UncommentedEmptyConstructor","AvoidConstantsInterface","UnsynchronizedStaticDateFormatter","PreserveStackTrace","UseCollectionIsEmpty","ClassWithOnlyPrivateConstructorsShouldBeFinal","EmptyMethodInAbstractClassShouldBeAbstract","SingularField","ReturnEmptyArrayRatherThanNull","AbstractClassWithoutAnyMethod","TooFewBranchesForASwitchStatement","LogicInversion","UseVarargs","FieldDeclarationsShouldBeAtStartOfClass","GodClass","AvoidProtectedMethodInFinalClassNotExtending","","","","","","","","","","","","","","","","",});
		comboModels[9] = new DefaultComboBoxModel(new String[]{"","EmptyCatchBlock","EmptyIfStmt","EmptyWhileStmt","EmptyTryBlock","EmptyFinallyBlock","EmptySwitchStatements","EmptySynchronizedBlock","EmptyStatementNotInLoop","EmptyInitializer","EmptyStatementBlock","EmptyStaticInitializer"});
		comboModels[10] = new DefaultComboBoxModel(new String[]{"","EmptyFinalizer","FinalizeOnlyCallsSuperFinalize","FinalizeOverloaded","FinalizeDoesNotCallSuperFinalize","FinalizeShouldBeProtected","AvoidCallingFinalize"});
		comboModels[11] = new DefaultComboBoxModel(new String[]{"","DuplicateImports","DontImportJavaLang","UnusedImports","ImportFromSamePackage","TooManyStaticImports","UnnecessaryFullyQualifiedName"});
		comboModels[12] = new DefaultComboBoxModel(new String[]{"","UseProperClassLoader","MDBAndSessionBeanNamingConvention","RemoteSessionInterfaceNamingConvention","LocalInterfaceSessionNamingConvention","LocalHomeNamingConvention","RemoteInterfaceNamingConvention","DoNotCallSystemExit","StaticEJBFieldShouldBeFinal","DoNotUseThreads"});
		comboModels[13] = new DefaultComboBoxModel(new String[]{"","BeanMembersShouldSerialize","MissingSerialVersionUID"});
		comboModels[14] = new DefaultComboBoxModel(new String[]{"","JUnitStaticSuite","JUnitSpelling","JUnitAssertionsShouldIncludeMessage","JUnitTestsShouldIncludeAssert","TestClassWithoutTestCases","UnnecessaryBooleanAssertion","UseAssertEqualsInsteadOfAssertTrue","UseAssertSameInsteadOfAssertTrue","UseAssertNullInsteadOfAssertTrue","SimplifyBooleanAssertion","JUnitTestContainsTooManyAsserts","UseAssertTrueInsteadOfAssertEquals"});
		comboModels[15] = new DefaultComboBoxModel(new String[]{"","UseCorrectExceptionLogging","ProperLogger","GuardDebugLogging","GuardLogStatement"});
		comboModels[16] = new DefaultComboBoxModel(new String[]{"","MoreThanOneLogger","LoggerIsNotStaticFinal","SystemPrintln","AvoidPrintStackTrace","GuardLogStatementJavaUtil"});
		comboModels[17] = new DefaultComboBoxModel(new String[]{"","ReplaceVectorWithList","ReplaceHashtableWithMap","ReplaceEnumerationWithIterator","AvoidEnumAsIdentifier","AvoidAssertAsIdentifier","IntegerInstantiation","ByteInstantiation","ShortInstantiation","LongInstantiation","JUnit4TestShouldUseBeforeAnnotation","JUnit4TestShouldUseAfterAnnotation","JUnit4TestShouldUseTestAnnotation","JUnit4SuitesShouldUseSuiteAnnotation","JUnitUseExpected"});
		comboModels[18] = new DefaultComboBoxModel(new String[]{"","ShortVariable","LongVariable","ShortMethodName","VariableNamingConventions","MethodNamingConventions","ClassNamingConventions","AbstractNaming","AvoidDollarSigns","MethodWithSameNameAsEnclosingClass","SuspiciousHashcodeMethodName","SuspiciousConstantFieldName","SuspiciousEqualsMethodName","AvoidFieldNameMatchingTypeName","AvoidFieldNameMatchingMethodName","NoPackage","PackageCase","MisleadingVariableName","BooleanGetMethodName","ShortClassName","GenericsNaming"});
		comboModels[19] = new DefaultComboBoxModel(new String[]{"","LocalVariableCouldBeFinal","MethodArgumentCouldBeFinal","AvoidInstantiatingObjectsInLoops","UseArrayListInsteadOfVector","SimplifyStartsWith","UseStringBufferForStringAppends","UseArraysAsList","AvoidArrayLoops","UnnecessaryWrapperObjectCreation","AddEmptyString","RedundantFieldInitializer","PrematureDeclaration"});
		comboModels[20] = new DefaultComboBoxModel(new String[]{"","AvoidCatchingThrowable","SignatureDeclareThrowsException","ExceptionAsFlowControl","AvoidCatchingNPE","AvoidThrowingRawExceptionTypes","AvoidThrowingNullPointerException","AvoidRethrowingException","DoNotExtendJavaLangError","DoNotThrowExceptionInFinally","AvoidThrowingNewInstanceOfSameException","AvoidCatchingGenericException","AvoidLosingExceptionInformation"});
		comboModels[21] = new DefaultComboBoxModel(new String[]{"","AvoidDuplicateLiterals","StringInstantiation","StringToString","InefficientStringBuffering","UnnecessaryCaseChange","UseStringBufferLength","AppendCharacterWithChar","ConsecutiveAppendsShouldReuse","ConsecutiveLiteralAppends","UseIndexOfChar","InefficientEmptyStringCheck","InsufficientStringBufferDeclaration","UselessStringValueOf","StringBufferInstantiationWithChar","UseEqualsToCompareStrings","AvoidStringBufferField"});
		comboModels[22] = new DefaultComboBoxModel(new String[]{"","MethodReturnsInternalArray","ArrayIsStoredDirectly"});
		comboModels[23] = new DefaultComboBoxModel(new String[]{"","LooseCoupling","CloneMethodMustImplementCloneable","UnusedImports","SignatureDeclareThrowsException"});
		comboModels[24] = new DefaultComboBoxModel(new String[]{"","UnnecessaryConversionTemporary","UnnecessaryReturn","UnnecessaryFinalModifier","UselessOverridingMethod","UselessOperationOnImmutable","UnusedNullCheckInEquals","UselessParentheses","UselessQualifiedThis"});
		comboModels[25] = new DefaultComboBoxModel(new String[]{"","UnusedPrivateField","UnusedLocalVariable","UnusedPrivateMethod","UnusedFormalParameter","UnusedModifier"});
		//comboModels[0] = new DefaultComboBoxModel(new String[]{"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",});

	
	
	}

	public RulesetCreator(String title){
		
		super(title);
		addWindowListener(this);
		initComboBoxes();
		Color gray=new Color(240,240,240);			/* Setting the background color */
		setLayout(new FlowLayout());				/* Setting the layout of the GUI */
		setBackground(gray);						/* Setting the background color */
	//
		//addWindowListener(this);					/* Adding the certain window listener for reacting to the user's
//													   commands for closing and minimizing the window.
//													*/
		String[] availRuleSets = {"android","basic","braces","clone","codesize","comments","controversial","coupling","design","empty","finalizers","imports","j2ee","javabeans","junit","logging-jakarta-commons","logging-java","migrating","naming","optimizations","strictexception","strings","sunsecure","typeresolution","unnecessary","unusedcode","unusedcode", "imports"};

		rulesets = new JComboBox(availRuleSets);
		rules = new JComboBox(comboModels[0]);
		rulesetName = new TextField();
		specificRuleName = new TextField();
		excludeRule = new TextField();
		fileName = new TextField();
		console = new JTextArea(10,40);
		console.setLineWrap(true);				
		console.setEditable(false);			
		JScrollPane sp=new JScrollPane(console);
		sp.setVerticalScrollBarPolicy(
			    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		create = new JButton("Create");
		add = new JButton("Add Rule");
		save = new JButton("Save");
		
		/* List Feature*/
		addFromList = new JButton("Add");
		
		rulesetName.setColumns(20);
		specificRuleName.setColumns(20);
		excludeRule.setColumns(20);
		fileName.setColumns(20);
		
		add(fileName);
		add(create);
		add(save);
		add(rulesetName);
		add(specificRuleName);
		add(excludeRule);
		add(add);
		add(sp);
		//add(console);
		add(rulesets);
		add(rules);
		add(addFromList);
		
		create.addActionListener(this);
		save.addActionListener(this);
		add.addActionListener(this);
		
		/* An action listener for the parent ComboBox */
		rulesets.addActionListener(this);
		addFromList.addActionListener(this);
	}
	
	public static void main(String[] args) throws Exception{
		
		RulesetCreator rc = new RulesetCreator("PMD RuleSet Creator");
		
		rc.setSize(520,320);				  /* Setting the size of the frame */
		rc.setVisible(true);				  /* Making the frame visible to the user */
		
	}
	
		public void actionPerformed(ActionEvent e){
			
			System.out.println("Action Listener Invoked");
			
			if(e.getSource()==create){
				System.out.println("Source found -> Create Button Pressed");
				String tempFileName = fileName.getText();
				console.append("The requested file name is: " + tempFileName + "\n");
				System.out.println("The requested file name is: " + tempFileName);
			 try{
				 	 writer = new BufferedWriter(
		                new FileWriter(tempFileName));
				 	console.append("Ruleset successfully opened" + "\n");
				 	System.out.println("Ruleset successfully opened");
				 	
				 	/* Writing the header */
				 	writer.write("<?xml version=\"1.0\"?>");
				 	writer.newLine();
				 	writer.write("<ruleset name=\"myRuleset\""
				 			+ " xmlns=\"http://pmd.sourceforge.net/ruleset/2.0.0\""
				 			+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
				 			+ " xsi:schemaLocation=\"http://pmd.sourceforge.net/ruleset/2.0.0 http://pmd.sourceforge.net/ruleset_2_0_0.xsd\" >");
				 	writer.newLine();
			 }catch(IOException ex){
				    System.out.println(" Exception !!!");
				 	System.out.println(ex.getMessage());
			 }
			 	fileName.setText("");
			 	
			}else if(e.getSource()==add){
				System.out.println("Source found -> Add Button Pressed");
				
				/* Check the fields and add the appropriate entry */
				try{
					writer.newLine();
					
					if("".equals(specificRuleName.getText())){
						writer.write("<rule ref=\"rulesets/java/" + rulesetName.getText() + "\"/>");
						System.out.println("<rule ref=\"rulesets/java/" + rulesetName.getText() + "\"/>");
					}else{
						writer.write("<rule ref=\"rulesets/java/" + rulesetName.getText() + "/" + specificRuleName.getText() + "\"/>");
						System.out.println("<rule ref=\"rulesets/java/" + rulesetName.getText() + "/" + specificRuleName.getText() + "\"/>");
					}
						
					writer.newLine();
				}catch(IOException ex){
					System.out.println("IOException caught while adding a rule to the ruleset.");
					System.out.println(ex.getMessage());
				}
				
				/* Inform the user that his rule is succesfully added to the ruleset */
				console.append("Rule entry: " + rulesetName.getText() + "/" + specificRuleName.getText() + " successfully added! \n");
				
				/* Clear the fields to accept the new entry */
				specificRuleName.setText("");
				rulesetName.setText("");
				
				
				
			}else if(e.getSource()==save){
				System.out.println("Source found -> Save Button Pressed");
				
				console.append("The ruleset is saved!! Check it out!!" + "\n");
				try{
					//writer.newLine();
					writer.write("</ruleset>");
					writer.flush();
					writer.close();
				}catch(IOException ex){
					System.out.println(ex.getMessage());
				}
					
			}else if (e.getSource()==rulesets){
				System.out.println();
				System.out.println("Ruleset selected");
				System.out.println("Updating the rules list!");
				System.out.println("Rules list updated");
				rules.setModel(comboModels[rulesets.getSelectedIndex()]);
				
			}else if(e.getSource()==addFromList){
				
				System.out.println("Source found -> Add From List Button Pressed");
				
				/* Check the fields and add the appropriate entry */
				try{
					writer.newLine();
					
					if("".equals(rules.getSelectedItem().toString())){
						writer.write("<rule ref=\"rulesets/java/" + rulesets.getSelectedItem().toString() + ".xml\"/>");
						System.out.println("<rule ref=\"rulesets/java/" + rulesets.getSelectedItem().toString() + ".xml\"/>");
					}else{
						writer.write("<rule ref=\"rulesets/java/" + rulesets.getSelectedItem().toString() + ".xml/" + rules.getSelectedItem().toString() + "\"/>");
						System.out.println("<rule ref=\"rulesets/java/" + rulesets.getSelectedItem().toString() + ".xml/" + rules.getSelectedItem().toString() + "\"/>");
					}
						
					writer.newLine();
				}catch(IOException ex){
					System.out.println("IOException caught while adding a rule to the ruleset.");
					System.out.println(ex.getMessage());
				}
				
				/* Inform the user that his rule is succesfully added to the ruleset */
				console.append("Rule entry: " + rulesets.getSelectedItem().toString() + ".xml/" + rules.getSelectedItem().toString() + " successfully added! \n");
				
				/* Clear the fields to accept the new entry */
				specificRuleName.setText("");
				rulesetName.setText("");
				
			}else{
					System.out.println("False Alarm!!");
				
			}
			System.out.println();
		}
		
		 public void windowClosing(WindowEvent arg0) {
			    System.exit(0);
			  }

			  public void windowOpened(WindowEvent arg0) {}
			  public void windowClosed(WindowEvent arg0) {}
			  public void windowIconified(WindowEvent arg0) {}
			  public void windowDeiconified(WindowEvent arg0) {}
			  public void windowActivated(WindowEvent arg0) {}
			  public void windowDeactivated(WindowEvent arg0) {}
  }


