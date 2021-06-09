package com.example.chatapp;

public class Users
    {
//        public LinkedList<ChatModel> getAllUsers()
//        {
//            return AllUsers;
//        }

//        LinkedList<ChatModel> AllUsers = new LinkedList<>();

       public String userName;
       String uID;
       String phoneNumber;
       String dp;
       String statusOnOff;
       String lastSeen;
       String lastMsg;

        public String getLastMsg()
        {
            return lastMsg;
        }

        public void setLastMsg(String lastMsg)
        {
            this.lastMsg = lastMsg;
        }



       public Users()
       {

       }
       public String getUserName()
        {
            return userName;
        }

        public void setUserName(String userName)
        {
            this.userName = userName;
        }

        public String getuID()
        {
            return uID;
        }

        public void setuID(String uID)
        {
            this.uID = uID;
        }

        public String getPhoneNumber()
        {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber)
        {
            this.phoneNumber = phoneNumber;
        }

        public String getDp()
        {
            return dp;
        }

        public void setDp(String dp)
        {
            this.dp = dp;
        }

        public String getStatusOnOff()
        {
            return statusOnOff;
        }

        public void setStatusOnOff(String statusOnOff)
        {
            this.statusOnOff = statusOnOff;
        }

        public String getLastSeen()
        {
            return lastSeen;
        }

        public void setLastSeen(String lastSeen)
        {
            this.lastSeen = lastSeen;
        }

        public Users(String userName, String uID, String phoneNumber, String dp, String statusOnOff, String lastSeen)
        {
            this.userName = userName;
            this.uID = uID;
            this.phoneNumber = phoneNumber;
            this.dp = dp;
            this.statusOnOff = statusOnOff;
            this.lastSeen = lastSeen;
        }

        public Users(String userName, String uID, String phoneNumber)
        {
            this(userName, uID, phoneNumber, "1", "off", "no");
        }
  }
