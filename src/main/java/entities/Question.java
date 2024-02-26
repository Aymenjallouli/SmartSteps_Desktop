package entities;

public class Question {
    int id , id_evaluation, num, max_score;
    String type , enonce, options, solution, concept;

    public Question(int id,int id_evaluation, int num, String type, String enonce, String options, String solution, String concept, int max_score) {
        this.id = id;
        this.id_evaluation = id_evaluation;
        this.num = num;
        this.type = type;
        this.enonce = enonce;
        this.options = options;
        this.solution = solution;
        this.concept = concept;
        this.max_score = max_score;
    }

    public Question() {
    }

    public Question(int id_evaluation, int num, String type, String enonce, String options, String solution, String concept, int max_score) {
        this.id_evaluation = id_evaluation;
        this.num = num;
        this.type = type;
        this.enonce = enonce;
        this.options = options;
        this.solution = solution;
        this.concept = concept;
        this.max_score = max_score;
    }


    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", id_eval=" + id_evaluation +
                ", num_question=" + num +
                ", type=" + type +
                ", enonce=" + enonce +
                ", options='" + options + '\'' +
                ", solution='" + solution + '\'' +
                ", concept='" + concept + '\'' +
                ", max_score='" + max_score + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId_evaluation() {
        return id_evaluation;
    }
    public void setId_evaluation(int id_evaluation) {
        this.id_evaluation = id_evaluation;
    }
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnonce() {
        return enonce;
    }

    public void setEnonce(String enonce) {
        this.enonce = enonce;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public int getMax_score() {
        return max_score;
    }

    public void setMax_score(int max_score) {
        this.max_score = max_score;
    }
}