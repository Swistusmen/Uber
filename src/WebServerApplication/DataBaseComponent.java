package WebServerApplication;

import CommonDataTypes.PersonalData;
import CommonDataTypes.Ride;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataBaseComponent {
    private String resourceDirectory;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;
    List<PersonalData> personalData;

    DataBaseComponent(String resourceDirectory)
    {
        this.resourceDirectory=resourceDirectory;
        this.personalData=new LinkedList<PersonalData>();
    }

    private File CheckIfFileExists(File file)
    {
        //File file=new File(this.resourceDirectory+"/"+filePath);
        if(file.exists())
            return file;
        return null;
    }

    public boolean AddPersonalData(PersonalData pData)
    {
        System.out.println("DataBase component welcomes you");
        if(true==checkIfDataAreInDB(pData))
            return true;
        personalData.add(pData);
        return false;
    }

    public boolean checkIfDataAreInDB(PersonalData pData)
    {
        List<Integer> indexes=new ArrayList<Integer>();
        for(PersonalData p:personalData)
        {
            if(pData.equals(p))
                return true;
        }
        return false;
    }

    public PersonalData GetPersonalData(PersonalData pData)
    {
        String fileName=pData.getAccountNumber()+".ser";
        File file=new File(fileName);
        //File file;
        //if((file=this.CheckIfFileExists(resourceDirectory+"/"+fileName))!=null)
        if((file=this.CheckIfFileExists(file))!=null)
        {
            try {
                fileInputStream = new FileInputStream(file);
                inputStream=new ObjectInputStream(fileInputStream);
                pData=(PersonalData) inputStream.readObject();
                fileInputStream.close();
                inputStream.close();
                return pData;
            }catch(Exception e)
            {
                System.out.println(e);
            }
        }
        return null;
    }

    public boolean UpdatePersonalData(PersonalData pData)
    {
        String fileName=pData.getAccountNumber()+".ser";
        File file=new File(fileName);
        if((file=this.CheckIfFileExists(file))!=null)
        {
            try {
                fileOutputStream = new FileOutputStream(this.resourceDirectory + "/" + fileName);
                outputStream = new ObjectOutputStream(fileOutputStream);
                outputStream.close();
                fileOutputStream.close();
            }catch(Exception e)
            {
                System.out.println(e);
            }
            return true;
        }
        return false;
    }

    public boolean AddARide(Ride ride)
    {
        String fileName=ride.inputAddress+"_"+ride.phoneClient+".ser";
        File file=new File(fileName);
        if((file=this.CheckIfFileExists(file))!=null) //should be changed to exception
        {
            return false;
        }
        file=new File(this.resourceDirectory+"/"+fileName);
        try {
            file.createNewFile();
        }catch (Exception e)
        {
            System.out.println(e);
        }
        try {
            fileOutputStream = new FileOutputStream(this.resourceDirectory + "/" + fileName);
            outputStream=new ObjectOutputStream(fileOutputStream);
            outputStream.close();
            fileOutputStream.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }
        return true;
    }

    public boolean UpdateRide(Ride ride)
    {
        String fileName=ride.inputAddress+"_"+ride.phoneClient+".ser";
        File file=new File(fileName);
        if((file=this.CheckIfFileExists(file))!=null)
        {
            try {
                fileOutputStream = new FileOutputStream(this.resourceDirectory + "/" + fileName);
                outputStream = new ObjectOutputStream(fileOutputStream);
                outputStream.close();
                fileOutputStream.close();
            }catch(Exception e)
            {
                System.out.println(e);
            }
            return true;
        }
        return false;
    }

    public Ride GetRide(Ride ride)
    {
        String fileName=ride.inputAddress+"_"+ride.phoneClient+".ser";
        File file=new File(fileName);
        if((file=this.CheckIfFileExists(file))!=null)
        {
            try {
                fileInputStream = new FileInputStream(file);
                inputStream=new ObjectInputStream(fileInputStream);
                ride=(Ride) inputStream.readObject();
                fileInputStream.close();
                inputStream.close();
                return ride;
            }catch(Exception e)
            {
                System.out.println(e);
            }
        }
        return null;
    }

    public Ride DeleteASerializedRide(Ride ride)
    {
        String fileName=ride.inputAddress+"_"+ride.phoneClient+".ser";
        File file=new File(fileName);
        if((file=this.CheckIfFileExists(file))!=null)
        {
            try{
                fileInputStream = new FileInputStream(file);
                inputStream=new ObjectInputStream(fileInputStream);
                ride=(Ride) inputStream.readObject();
                fileInputStream.close();
                inputStream.close();
                file.delete();
                return ride;
            }catch (Exception e)
            {
                System.out.println(e);
            }
        }
        return null;
    }

    public PersonalData DeleteASerializedPersonalData(PersonalData pData)
    {
        String fileName=pData.getAccountNumber()+".ser";
        File file=new File(fileName);
        if((file=this.CheckIfFileExists(file))!=null)
        {
            try{
                fileInputStream = new FileInputStream(file);
                inputStream=new ObjectInputStream(fileInputStream);
                pData=(PersonalData) inputStream.readObject();
                fileInputStream.close();
                inputStream.close();
                file.delete();
                return pData;
            }catch (Exception e)
            {
                System.out.println(e);
            }
        }
        return null;
    }

}
