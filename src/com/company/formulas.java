package com.company;

public class formulas {

    public static double euclidian_distance(double[] x_vector,double[] y_vector)
    {
        double sum=0;
        for (int i = 0; i < x_vector.length; i++) {
            sum+=Math.pow((x_vector[i]-y_vector[i]),2);
        }
        return  Math.sqrt(sum);
    }

    public static double exp_decay(int iteration,double initial_decay,int time_constant)
    {
        return initial_decay*Math.exp(-iteration/time_constant);
    }

    public static double gaussian_neighbour(int iteration,double initial_decay,int time_constant,double distance)
    {
        return Math.exp(-Math.pow(distance,2)/(2*Math.pow(exp_decay(iteration,initial_decay,time_constant),2)));
    }

    public static double mexican_hat_neighbour(int iteration,double initial_decay,int time_constant,double distance)
    {
        //https://books.google.com.tr/books?id=IUmvDwAAQBAJ&pg=PA237&lpg=PA237&dq=som+topological+mexican+hat+function&source=bl&ots=oDewaZiFlZ&sig=ACfU3U2IeHNpSqnS_hpaKapCtMocBV_How&hl=en&sa=X&ved=2ahUKEwiLp8iOvcToAhVdRBUIHYU8C2YQ6AEwBXoECAoQAQ#v=onepage&q=som%20topological%20mexican%20hat%20function&f=true
        //page 237
        double part_one = 3*Math.exp(-Math.pow(distance,2)/(Math.pow(exp_decay(iteration,initial_decay,time_constant),2)));
        double part_two = Math.exp(-Math.pow(distance,2)/(4*Math.pow(exp_decay(iteration,initial_decay,time_constant),2)));
        return (part_one-part_two)/2;
    }
}
