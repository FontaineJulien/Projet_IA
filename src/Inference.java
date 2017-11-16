
public class Inference {
	
	private Integer inference_number;
	private Rule rule;
	
	public Inference(Integer inference_number, Rule rule){
		this.inference_number = inference_number;
		this.rule = rule;
	}
	
	public void display(){
		System.out.println("Inférence n°"+inference_number+" : "+rule.toString());
	}
}
