# Employee Loader
Employee Loader serves 2 services - an employee CRUD rest service and a file loader to mass load employee data

## File Loader
File loader can be used to load multiple lines of employee data asynchronously. On sending a post request an immediate response is provided with file name and status link for the specific file. Expected statues are _PROCESSING_, _COMPLETED_, and _FAILED_.   

Data returned by employee CRUD rest service will be only the lines that were from a file with _COMPLETED_ although data from files in _PROCESSING_ are also present in the DB. Lines from files that are _FAILED_ will be removed when files are marked _FAILED_.

### Usage
Create a file in the format:  
_name1 age1_  
_name2 age2_  

**Rest Endpoint** : http://\<url\>/api/employee?action=upload  
**Content-Type** : multipart/form-data  
**Response** :   
{  
    "fileName": "sampleSmall.txt",  
    "fileId": "FILE_1611241771973",  
    "status": "http://\<url\>/api/status/FILE_1611241771973"  
}  

The status link can be used to get a current status of the file as the file load is aynchronous while this response is immediate.   
**Sample response for a GET on status**  
{  
    "fileId": "FILE_1611241771973",  
    "fileName": "sampleSmall.txt",  
    "status": "COMPLETE"  
}  

## Employee CRUD service
### Usage
#### POST  
**Rest Endpoint** : http://\<url\>/api/employee/  
**Content-Type** : application/json  
**Body** :  
{  
    "name": "Patrick Gelsinger",  
    "age": 59  
}  

**Sample Response Body** :  
{  
    "name": "Patrick Gelsinger",  
    "age": 59  
}   

#### GET all employees
**Rest Endpoint** : http://\<url\>/api/employee/  
**Content-Type** : application/json  
**Sample Response Body** :  
[  
  {    
    "id": 101,  
    "name": "Patrick Gelsinger",  
    "age": 59  
  }  
]  

#### GET a specific employee
**Rest Endpoint** : http://\<url\>/api/employee/\<id\>  
**Content-Type** : application/json  
**Sample Response Body** :  
{  
    "name": "Patrick Gelsinger",  
    "age": 59  
}  

#### PUT or update a specific employee
**Rest Endpoint** : http://\<url\>/api/employee/\<id\>  
**Content-Type** : application/json  
**Sample Response Body** :  
{  
    "id": 101,  
    "name": "Patrick P Gelsinger ",  
    "age": 60  
}  

#### DELETE a specific employee
**Rest Endpoint** : http://\<url\>/api/employee/\<id\>  

## Testing
Sample test input files have been provided *testdata* folder  
_sampleSmall.txt_ : Contains 5 lines of employee data  
_sampleWrong.txt_ : Contains 5 lines of employee data in which 2 have invalid data. All lines including the ones above and below the wrong lines will be rolled back.  
_sampleLarge.txt_ : Contains 1 million lines of dummy employee data to simulate asynchronous data load. Records that are loaded will be present in DB but marked as not yet available until whole file is processed.  








