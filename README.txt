Welcome to opentaps-mailer - A scheduled follow-up marketing campaign mailer (based on Opentaps/OFBiz)!!

Description:
    opentaps-mailer provides a flexible email and print follow-up marketing campaign managament service built atop the Opentaps/OFBiz framework. One can easily create a campaign and schedule to send the mails to an externally imported set of contacts in a few clicks. The email templates associated with a campaign are highly customizable and can be done so via a rich WYSIWYG editor. Custom mapping of contacts files to be imported is possible. An information rich campaign management screen is available to track, execute and administer the campaigns.  

Pre-requisite:
    To run the opentaps-mailer you need to have a base installation of Opentaps 1.0. If you don't have Opentaps you can download from here - http://www.opentaps.org/products/download and follow the installation instructions on its site.

Installation:
    1. After installing Opentaps, checkout the code to hot-deploy/mailer directory.
    2. Check the contents of the file hot-deploy/mailer/docs/core-diff.txt. Ensure that the files in lines starting with D are deleted and those in lines starting with A are added to the respective paths.
    Replace the file at framework/entity/src/org/ofbiz/entity/model/ModelField.java with the file provided herein.
    The requisite files are present in the docs/externallib directory.  
    3. Next to build the module execute ./ant
    4. After the build is successful, execute ./startofbiz.sh and access the application at http://localhost:8080/mailer.
    5. To login use the following credentials - username / password - "admin" / "opentaps" respectively.

Quick tutorial:
    1. First and foremost you are required to define a mapping based on your contact list import format. The same can be done from the Configure Mappings section.
    2. To create your contact list click on Create contact list on the Shortcut menu. Fill the necessary details, submit the form, and thereafter import your contact list.
    3. Next, to create your template go to Campaign Templates click on New template. You can schedule the template by entering a value in the Schedule At N + field in Campaign Templates form, where N is the sale/service date.
    4. Eventually, create your campaign by clicking on Create Campaign option. In this form choose the appropriate template and contact list that you created earlier.
    5. After submitting the Create Campaign form go to find Campaign option in the shortcut option where you shall be able to see your campaign.

 NOTE:
    The current release has only been tested on Opentaps 1.0.