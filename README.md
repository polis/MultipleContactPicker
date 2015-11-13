# MultipleContactPicker
Android library for multiple contact selection

Steps to use:

1. Add it as an android lybrary to your project
2. Then in your code, to show multiple contact picker use          

    Intent contactPickerIntent = new Intent(getContext(), MultipleContactPickerActivity.class);
    getActivity().startActivityForResult(contactPickerIntent, CONTACT_PICKER_REQUEST);

3. Next in your activity class 

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTACT_PICKER_REQUEST ) {
            if (resultCode == RESULT_OK) {

                ArrayList<Contact> contacts = new ArrayList<Contact>();
                        contacts = data.getParcelableArrayListExtra("contacts");

                for (Contact c: contacts){
                    Log.d("Selected contact = ", c.getEmail());
                }
                //data.
                // A contact was picked.  Here we will just display it
                // to the user.
            }
        }
    }
    
4. Contact is parcalabele class that contains display name, photo and email so you can use it in your code or extend by attributes you need. 
