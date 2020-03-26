package com.sym.dataStructure.stack.example;


import com.sym.dataStructure.stack.arrayStack.MyArrayStack;

/**
 * 分隔符匹配问题，编写判断Java语句中分隔符是否匹配的程序
 * @author Administrator
 */
public class CheckJavaSyn {
	
	public static void main(String[] args) {
		CheckJavaSyn cjs = new CheckJavaSyn();
		String str = "(/*1**/)";
		cjs.check(str);
	}
	
	//栈结构，用于保存左分隔符并匹配右分隔符
	private MyArrayStack stack = new MyArrayStack(100);
	
	//左分隔符返回1，右分隔符返回2，其他分隔符返回3
	private static final int ISLEAF = 1;
	private static final int ISRIGHT = 2;
	private static final int OTHER = 3;
	
	//判断指定字符串属于哪个分隔符
	private int verifyFlag(String c){
		if(c.equals("(") || c.equals("{") || c.equals("[") || c.equals("/*"))
			return ISLEAF;
		else if(c.equals(")") || c.equals("}") || c.equals("]") || c.equals("*/"))
			return ISRIGHT;
		else 
			return OTHER;   
	}
	
	//匹配分隔符，即"("要与")"匹配，返回true，不匹配返回false
	private boolean matching(String left,String right){
		 if((left.equals("(") && right.equals(")"))
			||(left.equals("{") && right.equals("}"))
			||(left.equals("[") && right.equals("]"))
			||(left.equals("/*") && right.equals("*/")))
			return true;
		else 
			return false;
	}
	
	//核心方法，遍历字符串，如果是左边符放到栈中，如果是右边符，取出栈顶元素，如果不匹配返回false，如果匹配继续循环
	private boolean isLegal(String str){
		
		for(int i=0;i<str.length();i++){
			String temp = str.charAt(i)+"";
			if(i!=str.length()){
				if((temp.equals("/") && "*".equals(str.charAt(i+1))) || (temp.equals("*") && "/".equals(i+1))){
					temp = temp.concat(str.charAt(++i)+"");
				}
			}
			/* 上面的判断方式会不会更简单一点呢？
			 * if(temp.equals("/")){
				String tmp = str.charAt(i+1)+"";
				if(tmp.equals("*")){
					temp = temp+tmp;
					i++;
				}	
			}
			if(temp.equals("*")){
				String tmp = str.charAt(i+1)+"";
				if(tmp.equals("/")){
					temp = temp+tmp;
					i++;
				}
			}*/
			int o = verifyFlag(temp);
			if(o==ISLEAF){
				stack.push(temp);
			}else if(o==ISRIGHT){
				String left = stack.pop()+"";
				if(stack.isEmpty() || !matching(left, temp)){
					return false;
				}
			}
		}
		if(!stack.isEmpty())
			return false;
		return true;
	}	
	
	public void check(String str){
		if(isLegal(str)){
			System.out.println("语句正确");
		}else {
			System.out.println("语句不合法");
		}
	}
	
}
