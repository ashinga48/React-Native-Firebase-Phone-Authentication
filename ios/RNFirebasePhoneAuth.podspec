
require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = "RNFirebasePhoneAuth"
  s.version      = "1.0.0"
  s.summary      = "RNFirebasePhoneAuth"
  s.description  = <<-DESC
                  React Native Plugin [ only firebase phone authentication ]
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/ashinga48/React-Native-Firebase-Phone-Authentication.git", :tag => "master" }
  s.source_files  = "RNFirebasePhoneAuth/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  s.dependency "Firebase"
  #s.dependency "others"

end
